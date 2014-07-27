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

import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.search.index.util.StringTool;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserPlannerService;
/**
 * 规划师增量索引
 */
@Component
public class AppendPlanners  {
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	@Autowired
	private ExportPlanners exp;
	
	@Autowired
	private SolrUtil solrUtil;
	
	private SolrServer[] servers;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Log log = LogFactory.getLog(AppendPlanners.class);

	@Scheduled(cron="0 0/5 * * * ?")
	public void executue() {
		
		if (System.getProperty("AppendPlanner")!=null && "start".equals(System.getProperty("AppendPlanner"))) {
			log.info("Append Planner is running,this Exit!!");
			return;
		}
		
		if (System.getProperty("ALLPlanner")!=null && "start".equals(System.getProperty("ALLPlanner"))) {
			log.info("Export all Planner is running,this Exit!!");
			return;
		}
		long s = System.currentTimeMillis();
		log.info("Planner增量索引开始...");
		try {
			System.setProperty("AppendPlanner", "start");
			exportPlannerVos();
			long e = System.currentTimeMillis();
			log.info("Planner索引增量完,用时 :  "+(e-s)+" 毫秒！");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 System.setProperty("AppendPlanner", "end");
		}
	}

	private void exportPlannerVos() {
		int startID = 0, batchSize = 1000;
		List<UserPlanner> list = null;
		Date[] lastRunning=getLastRunningDate();
		Date endTime = new Date();
		if(endTime==null) return;
		if(lastRunning==null){
			log.error("Planner时间文件出错！");
			return;
		}else if(!endTime.after(lastRunning[1])){
			log.info("Planner本次更新操作记录为  0！");
			return;
		}else{
			servers = solrUtil.getLryPlannerServers();
		}
		//==查询是否有需要更新的记录
		do {
			try {
				list = userPlannerService.getUserPlannerListForSearchIndex(lastRunning[1], endTime, startID, batchSize);
	            List<List<UserPlanner>> lists=assignServer(list, servers.length);
				
				for(int i=0;i<servers.length;i++){
					if(lists.get(i).size()==0){
						continue;
					}
					exp.export(servers[i], lists.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			startID = startID + batchSize;
		} while (list.size() == batchSize);
		StringTool.WriteContentToTextFile(AppConfigs.getInstance().get("search_index_export_record_planner"), sdf.format(lastRunning[1])+"#"+sdf.format(endTime));
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
		
		for(UserPlanner p: list){
			for(int i=0;i<size;i++){
				if(p.getId()%size==i){
					lists.get(i).add(p);
					break;
				}
			}
		}
		
		return lists;
	}
	
	
	private Date[] getLastRunningDate(){
		try {
			String str=StringTool.getTextFileContent(AppConfigs.getInstance().get("search_index_export_record_planner"));	
			if(str==null){
				return null;
			}
			String[] array=str.split("#");
			Date[] darray=new Date[2];
			darray[0]=sdf.parse(array[0].trim());
			darray[1]=sdf.parse(array[1].trim());
			return darray;
		} catch (Exception e) {
			log.error("Planner从时间文件中读取数据出错,异常信息： "+e.toString());
			e.printStackTrace();
			return null;
		}
	}

}
