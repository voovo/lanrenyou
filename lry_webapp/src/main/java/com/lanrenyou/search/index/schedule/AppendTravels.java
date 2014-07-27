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
import com.lanrenyou.search.index.ExportTravels;

import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.search.index.util.StringTool;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.service.ITravelContentService;
/**
 * 游记增量索引
 */
@Component
public class AppendTravels  {
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private ExportTravels exp;
	
	@Autowired
	private SolrUtil solrUtil;
	
	private SolrServer[] servers;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Log log = LogFactory.getLog(AppendTravels.class);

	@Scheduled(cron="0 0/5 * * * ?")
//	@Scheduled(cron="0/10 * * * * ?")
	public void executue() {
		
		if (System.getProperty("AppendTravel")!=null && "start".equals(System.getProperty("AppendTravel"))) {
			log.info("Append Travel is running,this Exit!!");
			return;
		}
		
		if (System.getProperty("ALLTravel")!=null && "start".equals(System.getProperty("ALLTravel"))) {
			log.info("Export all Travel is running,this Exit!!");
			return;
		}
		long s = System.currentTimeMillis();
		log.info("Travel增量索引开始...");
		try {
			System.setProperty("AppendTravel", "start");
			exportTravelVos();
			long e = System.currentTimeMillis();
			log.info("Travel索引增量完,用时 :  "+(e-s)+" 毫秒！");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 System.setProperty("AppendTravel", "end");
		}
	}

	private void exportTravelVos() {
		int startID = 0, batchSize = 1000;
		List<TravelContent> list = null;
		Date[] lastRunning=getLastRunningDate();
		Date endTime = new Date();
		if(endTime==null) return;
		if(lastRunning==null){
			log.error("Travel时间文件出错！");
			return;
		}else if(!endTime.after(lastRunning[1])){
			log.info("Travel本次更新操作记录为  0！");
			return;
		}else{
			servers = solrUtil.getLryTravelServers();
		}
		//==查询是否有需要更新的记录
		do {
			try {
				list = travelContentService.getTravelContentListForSearchIndex(lastRunning[1], endTime, startID, batchSize);
	            List<List<TravelContent>> lists=assignServer(list, servers.length);
				
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
		StringTool.WriteContentToTextFile(AppConfigs.getInstance().get("search_index_export_record_travel"), sdf.format(lastRunning[1])+"#"+sdf.format(endTime));
		try {
			for(SolrServer server:servers){
				server.optimize();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<List<TravelContent>> assignServer(List<TravelContent> list,int size){
		ArrayList<List<TravelContent>> lists=new ArrayList<List<TravelContent>>(size);
		for(int i=0;i<size;i++){
			lists.add(new ArrayList<TravelContent>());
		}
		
		for(TravelContent t: list){
			for(int i=0;i<size;i++){
				if(t.getId()%size==i){
					lists.get(i).add(t);
					break;
				}
			}
		}
		
		return lists;
	}
	
	
	private Date[] getLastRunningDate(){
		try {
			String str=StringTool.getTextFileContent(AppConfigs.getInstance().get("search_index_export_record_travel"));	
			if(str==null){
				return null;
			}
			String[] array=str.split("#");
			Date[] darray=new Date[2];
			darray[0]=sdf.parse(array[0].trim());
			darray[1]=sdf.parse(array[1].trim());
			return darray;
		} catch (Exception e) {
			log.error("Travel从时间文件中读取数据出错,异常信息： "+e.toString());
			e.printStackTrace();
			return null;
		}
	}

}
