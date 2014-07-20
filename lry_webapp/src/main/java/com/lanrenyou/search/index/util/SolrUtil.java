package com.lanrenyou.search.index.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.google.gson.Gson;
import com.lanrenyou.travel.model.TravelInfo;

public class SolrUtil {

	private String lryTravelServer;
	
	private String lryPlannerServer;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	public String getLryTravelServer() {
		return lryTravelServer;
	}

	public void setLryTravelServer(String lryTravelServer) {
		this.lryTravelServer = lryTravelServer;
	}

	public String getLryPlannerServer() {
		return lryPlannerServer;
	}

	public void setLryPlannerServer(String lryPlannerServer) {
		this.lryPlannerServer = lryPlannerServer;
	}

	public SolrServer[] getLryTravelServers() {
		String[] array=lryTravelServer.split(",");
		CommonsHttpSolrServer[] servers = new CommonsHttpSolrServer[array.length];
		for(int i=0;i<array.length;i++){
			try {
				servers[i] = new CommonsHttpSolrServer(array[i]);
				servers[i].setConnectionTimeout(100); // 1/10th sec
				servers[i].setDefaultMaxConnectionsPerHost(100);
				servers[i].setMaxTotalConnections(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return servers;
	}
	
	public SolrServer[] getLryPlannerServers() {
		String[] array=lryPlannerServer.split(",");
		CommonsHttpSolrServer[] servers = new CommonsHttpSolrServer[array.length];
		for(int i=0;i<array.length;i++){
			try {
				servers[i] = new CommonsHttpSolrServer(array[i]);
				servers[i].setConnectionTimeout(100); // 1/10th sec
				servers[i].setDefaultMaxConnectionsPerHost(100);
				servers[i].setMaxTotalConnections(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return servers;
	}

	public List<TravelInfo> searchTravel(String keyword, String city, int pageNo, int pageSize){
		SolrQuery query = new SolrQuery();
		if(pageNo < 1){
			pageNo = 1;
		}
		if(pageSize < 1){
			pageSize = 10;
		}
		query.setStart((pageNo -1) * pageSize);
		query.setRows(pageSize);
		StringBuilder querySb = new StringBuilder();
		if(StringUtils.isNotBlank(city)){
			querySb.append("city:").append(city);
		}
		if(StringUtils.isNotBlank(keyword)){
			StringBuilder keywordSb = new StringBuilder("(");
			keywordSb.append("title:").append(keyword).append(" or ");
			keywordSb.append("city:").append(keyword).append(" or ");
			keywordSb.append("content:").append(keyword).append(" or ");
			keywordSb.append("user_name:").append(keyword).append(" or ");
			keywordSb.append("user_nickname:").append(keyword).append(" or ");
			keywordSb.append("planner_targetCity:").append(keyword).append(" or ");
			keywordSb.append("user_userIntro:").append(keyword);
			keywordSb.append(")");
			
			if(querySb.length() > 0){
				querySb.append(" and ").append(keywordSb);
			} else {
				querySb.append(keywordSb.substring(1, keywordSb.length() -1 ));
			}
		}
		
		query.setQuery(querySb.toString());
		
		query.setSortField("updateTime", ORDER.desc);
		
		try {
			QueryResponse resp = this.getLryPlannerServers()[0].query(query);
			SolrDocumentList docList = resp.getResults();
			Iterator<SolrDocument> docIter = docList.iterator();
			List<TravelInfo> travelInfoList = new ArrayList<TravelInfo>();
			while(docIter.hasNext()){
				SolrDocument doc = docIter.next();
				Integer tid = (Integer) doc.getFieldValue("tid");
				String title = (String) doc.getFieldValue("titile");
				Integer uid = (Integer) doc.getFieldValue("uid");
				String createTimeStr = (String) doc.getFieldValue("create_time");
				String content = (String) doc.getFieldValue("content");
				Date createTime = sdf.parse(createTimeStr);
				TravelInfo travelInfo = new TravelInfo();
				travelInfo.setId(tid);
				travelInfo.setTitle(title);
				travelInfo.setUid(uid);
				travelInfo.setCreateTime(createTime);
				travelInfo.setContent(content);
				travelInfoList.add(travelInfo);
			}
			return travelInfoList;
		} catch (SolrServerException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws MalformedURLException{
		Gson gson = new Gson();
		SolrQuery query = new SolrQuery();
		query.setStart(0);
		query.setRows(10);
		String keyword = "asas";
		String city = null;
		StringBuilder querySb = new StringBuilder();
		if(StringUtils.isNotBlank(city)){
			querySb.append("city:").append(city);
		}
		if(StringUtils.isNotBlank(keyword)){
			StringBuilder keywordSb = new StringBuilder("(");
			keywordSb.append("title:").append(keyword).append(" or ");
			keywordSb.append("city:").append(keyword).append(" or ");
			keywordSb.append("content:").append(keyword).append(" or ");
			keywordSb.append("user_name:").append(keyword).append(" or ");
			keywordSb.append("user_nickname:").append(keyword).append(" or ");
			keywordSb.append("planner_targetCity:").append(keyword).append(" or ");
			keywordSb.append("user_userIntro:").append(keyword);
			keywordSb.append(")");
			
			if(querySb.length() > 0){
				querySb.append(" and ").append(keywordSb);
			} else {
				querySb.append(keywordSb.substring(1, keywordSb.length() -1 ));
			}
		}
		
		query.setQuery(querySb.toString());
		
		query.setSortField("updateTime", ORDER.desc);
		
		System.out.println(gson.toJson(query));
		try {
			CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://search.lanrenyou.com/travel/");
			server.setConnectionTimeout(10000); // 1/10th sec
			server.setDefaultMaxConnectionsPerHost(100);
			server.setMaxTotalConnections(100);
			QueryResponse resp = server.query(query, METHOD.POST);
			SolrDocumentList docList = resp.getResults();
			Iterator<SolrDocument> docIter = docList.iterator();
			while(docIter.hasNext()){
				System.out.println(gson.toJson(docIter.next()));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
