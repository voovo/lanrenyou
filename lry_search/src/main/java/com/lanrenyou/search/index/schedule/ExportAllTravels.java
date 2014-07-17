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

import com.lanrenyou.search.index.ExportTravels;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.search.index.util.StringTool;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelInfoService;

/**
 * 全量创建索引
 */
@Component
public class ExportAllTravels  {
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private ExportTravels exp;
	
	@Autowired
	private SolrUtil solrUtil;
	
	private SolrServer[] servers;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private String filePath="D:/tmp/data/exportTravel";
	
	private Log log = LogFactory.getLog(ExportAllTravels.class);

	@Scheduled(cron="0 12 4 * * ?")
//	@Scheduled(cron="0/20 * * * * ?")
	public void executue() {
		if (System.getProperty("ALLTravel")!=null && "start".equals(System.getProperty("ALLTravel"))) {
			log.info("export all Travel is running.....");
			return;
		}
		long s = System.currentTimeMillis();
		log.info("Export All Travel ...............................");
		try {
			System.setProperty("ALLTravel", "start");
			servers = solrUtil.getLryTravelServers();
			deleteAllIndex();
			log.info("删除先前的索引文件完成！");
			exportTravelVos();
			long e = System.currentTimeMillis();
			log.info("索引重建完成,用时  : "+(e-s)+" 毫秒！");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 System.setProperty("ALLTravel", "end");
		}
	}

	private void exportTravelVos() {
		int startID = 0, batchSize = 1000;
		List<TravelInfo> list = null;
		Date endTime = new Date();
		if(endTime==null) return;
		do {
			try {
				list = travelInfoService.getTravelInfoListForSearchIndex(endTime, startID, batchSize);
				log.info(" Travel Size:" + list.size());
	            List<List<TravelInfo>> lists=assignServer(list, servers.length);
				
				for(int i=0;i<servers.length;i++){
					if(lists.get(i).size()==0){
						continue;
					}
					exp.export(servers[i], lists.get(i));
					log.info(" ADD Travel Index:" + list.get(i).getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			startID = startID + batchSize;
		} while (list.size() == batchSize);
		StringTool.WriteContentToTextFile(filePath, sdf.format(endTime)+"\n"+sdf.format(endTime));
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
