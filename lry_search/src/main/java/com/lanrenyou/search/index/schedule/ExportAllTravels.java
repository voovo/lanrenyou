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
import com.baidu.job.search.index.ExportWishs;
import com.baidu.job.search.index.domain.campus.TravelInfo;
import com.baidu.job.search.index.repository.campus.TravelInfoReository;

import com.baidu.job.search.index.util.SolrUtil;
import com.baidu.job.search.index.util.SpringTool;
import com.baidu.job.search.index.util.StringTool;

/**
 * 
 *
 * @author zhaolei
 * create time:2011-06
 *  修改记录：
 */
@Component
public class ExportAllTravels  {
	
	@Autowired
	private TravelInfoReository repo;
	
	@Autowired
	private ExportTravels exp;
	
	@Autowired
	private SolrUtil solrUtil;
	private SolrServer[] servers;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String filePath="/WEB-INF/data/exportWish";
	private Log log = LogFactory.getLog(ExportAllTravels.class);

	@Scheduled(cron="0 12 4 * * ?")
	public void executue() {
		if (System.getProperty("ALL")!=null && "start".equals(System.getProperty("ALL"))) {
			log.info("export all crWishApply is running.....");
			return;
		}
		long s = System.currentTimeMillis();
		log.info("Export All crWishApply ...............................");
		try {
			System.setProperty("ALL", "start");
			servers = solrUtil.getLryServers();
			deleteAllIndex();
			log.info("删除先前的索引文件完成！");
			exportWishVos();
			long e = System.currentTimeMillis();
			log.info("索引重建完成,用时  : "+(e-s)+" 毫秒！");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 System.setProperty("ALL", "end");
		}
	}

	private void exportWishVos() {
		int startID = 0, batchSize = 1000;
		List<TravelInfo> list = null;
		Date endTime = repo.getMaxModifyDate();
		if(endTime==null) return;
		do {
			try {
				list = repo.getCrWishApplyVos(startID, batchSize,endTime);
	            List<List<TravelInfo>> lists=assignServer(list, servers.length);
				
				for(int i=0;i<servers.length;i++){
					if(lists.get(i).size()==0)continue;
					exp.export(servers[i], lists.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			startID = startID + batchSize;
		} while (list.size() == batchSize);
		StringTool.WriteContentToTextFile(SpringTool.getRealPath(filePath), sdf.format(endTime)+"\n"+sdf.format(endTime));
		try {
			for(SolrServer server:servers){
				server.optimize();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<List<TravelInfo>> assignServer(List<TravelInfo> list,int size){
		ArrayList<List<TravelInfo>> lists=new ArrayList<List<TravelInfo>>(size);
		for(int i=0;i<size;i++){
			lists.add(new ArrayList<TravelInfo>());
		}
		
		for(TravelInfo wish: list){
			for(int i=0;i<size;i++){
				if(wish.getCorpId()%size==i){
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
