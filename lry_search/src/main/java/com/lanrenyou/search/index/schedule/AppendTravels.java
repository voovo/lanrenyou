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
/**
 * 游记增量索引
 */

@Component
public class AppendTravels  {
	
	@Autowired
	private TravelInfoReository repo;
	
	@Autowired
	private ExportTravels exp;
	
	@Autowired
	private SolrUtil solrUtil;
	private SolrServer[] servers;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String filePath="/WEB-INF/data/exportWish";
	private Log log = LogFactory.getLog(AppendTravels.class);

	@Scheduled(cron="0 0/5 * * * ?")
	public void executue() {
		
		if (System.getProperty("Append")!=null && "start".equals(System.getProperty("Append"))) {
			log.info("Append crWishApply is running,this Exit!!");
			return;
		}
		
		if (System.getProperty("ALL")!=null && "start".equals(System.getProperty("ALL"))) {
			log.info("Export all crWishApply is running,this Exit!!");
			return;
		}
		long s = System.currentTimeMillis();
		log.info("增量索引开始...");
		try {
			System.setProperty("Append", "start");
			exportWishVos();
			long e = System.currentTimeMillis();
			log.info("索引增量完,用时 :  "+(e-s)+" 毫秒！");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 System.setProperty("Append", "end");
		}
	}

	private void exportWishVos() {
		int startID = 0, batchSize = 1000;
		List<TravelInfo> list = null;
		Date[] lastRunning=getLastRunningDate();
		Date endTime = repo.getMaxModifyDate();
		if(endTime==null) return;
		if(lastRunning==null){
			log.error("时间文件出错！");
			return;
		}else if(!endTime.after(lastRunning[1])){
			log.info("本次更新操作记录为  0！");
			return;
		}else{
			servers = solrUtil.getLryServers();
		}
		//==查询是否有需要更新的记录
//		int amount = repo.getCountCrWishApplyVos(lastRunning[1],endTime);
//		if(amount<=0){
//			log.info("本次更新操作记录为  0！");
//			return;
//		}else{
//			servers = solrUtil.getCampusServers();
//		}
		//==
		do {
			try {
				list = repo.getCrWishApplyVos(startID, batchSize,lastRunning[1],endTime);
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
		StringTool.WriteContentToTextFile(SpringTool.getRealPath(filePath), sdf.format(lastRunning[1])+"\n"+sdf.format(endTime));
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
	
	
	private Date[] getLastRunningDate(){
		try {
			
			String str=StringTool.getTextFileContent(SpringTool.getRealPath(filePath));	
			if(str==null){
				return null;
			}
			String[] array=str.split("\n");
			Date[] darray=new Date[2];
			darray[0]=sdf.parse(array[0].trim());
			darray[1]=sdf.parse(array[1].trim());
			
			return darray;
		} catch (Exception e) {
			log.error("从时间文件中读取数据出错,异常信息： "+e.toString());
			e.printStackTrace();
			return null;
		}
	}

}
