package com.lanrenyou.search.index.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lanrenyou.config.AppConfigs;
import com.lanrenyou.search.index.ExportPlanners;
import com.lanrenyou.search.index.ExportTravels;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.search.index.util.StringTool;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserPlannerService;

/**
 * 全量创建索引
 */
@Component
public class ExportAllPlanners  {
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	@Autowired
	private ExportPlanners exp;
	
	@Autowired
	private SolrUtil solrUtil;
	
	private SolrServer[] servers;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Log log = LogFactory.getLog(ExportAllPlanners.class);

	@Scheduled(cron="0 12 3 * * ?")
	public void executue() {
		if (System.getProperty("ALLPlanner")!=null && "start".equals(System.getProperty("ALLPlanner"))) {
			log.info("export all Planner is running.....");
			return;
		}
		long s = System.currentTimeMillis();
		log.info("Export All Planner ...............................");
		try {
			System.setProperty("ALLPlanner", "start");
			servers = solrUtil.getLryPlannerServers();
			deleteAllIndex();
			log.info("Planner删除先前的索引文件完成！");
			exportPlannerVos();
			long e = System.currentTimeMillis();
			log.info("Planner索引重建完成,用时  : "+(e-s)+" 毫秒！");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 System.setProperty("ALLPlanner", "end");
		}
	}

	private void exportPlannerVos() {
		int startID = 0, batchSize = 1000;
		List<UserPlanner> list = null;
		Date endTime = new Date();
		if(endTime==null) return;
		do {
			try {
				list = userPlannerService.getUserPlannerListForSearchIndex(endTime, startID, batchSize);
	            List<List<UserPlanner>> lists=assignServer(list, servers.length);
				
				for(int i=0;i<servers.length;i++){
					if(lists.get(i).size()==0)continue;
					exp.export(servers[i], lists.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			startID = startID + batchSize;
		} while (list.size() == batchSize);
		StringTool.WriteContentToTextFile(AppConfigs.getInstance().get("search_index_export_record_planner"), sdf.format(endTime)+"#"+sdf.format(endTime));
		try {
			for(SolrServer server:servers){
				server.optimize();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<List<UserPlanner>> assignServer(List<UserPlanner> list,int size){
		ArrayList<List<UserPlanner>> lists=new ArrayList<List<UserPlanner>>(size);
		for(int i=0;i<size;i++){
			lists.add(new ArrayList<UserPlanner>());
		}
		
		for(UserPlanner wish: list){
			for(int i=0;i<size;i++){
				if(wish.getId()%size==i){
					lists.get(i).add(wish);
					break;
				}
			}
		}
		
		return lists;
	}
	
	private void deleteAllIndex() throws Exception {
		for (SolrServer server:servers) {
			server.deleteByQuery("*:*");
			server.commit();
			server.optimize();
		}
	}
}
