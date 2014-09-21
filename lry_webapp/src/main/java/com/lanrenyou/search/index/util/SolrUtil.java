package com.lanrenyou.search.index.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.user.model.UserInfo;

public class SolrUtil {

	private String lryTravelServer;
	
	private String lryPlannerServer;
	
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
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
	
	public void delTravelByTid(int tid){
		try {
			this.getLryTravelServers()[0].deleteById(String.valueOf(tid));
			this.getLryTravelServers()[0].commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除索引文件[id=" + tid + "]出错了！");
		}
	}
	
	public void delTravelByTidList(List<Integer> tidList){
		if(null == tidList || tidList.size() <= 0){
			return ;
		}
		for(Integer tid :tidList){
			try {
				this.getLryTravelServers()[0].deleteById(String.valueOf(tid));
				this.getLryTravelServers()[0].commit();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除索引文件[id=" + tid + "]出错了！");
			}
		}
	}

	public PageIterator<TravelInfo> searchTravel(String keyword, String city, Integer createUid, int pageNo, int pageSize, String orderBy, boolean orderDesc){
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
			city = city.replace(" ", ",");
			city = city.replace("、", ",");
			city = city.replace("，", ",");
			city = city.replace("。", ",");
			city = city.replace("……", ",");
			city = city.replace(".", ",");
			String[] cityArray = city.split(",");
			if(cityArray.length == 1){
				querySb.append("city:").append(city).append(" or ");
				querySb.append("title:").append(city).append(" or ");
				querySb.append("content:").append(city);
			} else {
				querySb.append("( city:").append(cityArray[0]).append(" or ");
				querySb.append("title:").append(cityArray[0]).append(" or ");
				querySb.append("content:").append(cityArray[0]);
				for(int i=1; i<=cityArray.length-1; i++){
					if(StringUtils.isNotBlank(cityArray[i])){
						querySb.append(" or ").append("city:").append(cityArray[i]).append(" or ");
						querySb.append("title:").append(cityArray[i]).append(" or ");
						querySb.append("content:").append(cityArray[i]);
					}
					if(i == cityArray.length -1){
						querySb.append(")");
					}
				}
			}
		}
		if(null != createUid && createUid > 0){
			if(querySb.length() > 0){
				querySb.append(" and ").append("uid:").append(createUid);
			} else {
				querySb.append("uid:").append(createUid);
			}
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
		if(querySb.length() <= 0){
			querySb.append("*:*");
		}
		query.setQuery(querySb.toString());
		query.setFilterQueries("status:1 or status:2");
		
		if(StringUtils.isBlank(orderBy)){
			query.setSortField("updateTime", ORDER.desc);
		} else {
			query.setSortField(orderBy, orderDesc ?ORDER.desc:ORDER.asc);
		}
		List<TravelInfo> travelInfoList = null;
		Integer totalCount = 0;
		
		try {
			QueryResponse resp = this.getLryTravelServers()[0].query(query);
			SolrDocumentList docList = resp.getResults();
			totalCount = (int) docList.getNumFound();	// 命中个数
			Iterator<SolrDocument> docIter = docList.iterator();
			travelInfoList = new ArrayList<TravelInfo>();
			while(docIter.hasNext()){
				SolrDocument doc = docIter.next();
				Integer tid = (Integer) doc.getFieldValue("tid");
				String title = (String) doc.getFieldValue("title");
				Integer uid = (Integer) doc.getFieldValue("uid");
				String createTimeS = doc.getFieldValue("createTime").toString();
				String content = (String) doc.getFieldValue("content");
				TravelInfo travelInfo = new TravelInfo();
				travelInfo.setId(tid);
				travelInfo.setTitle(title);
				travelInfo.setUid(uid);
				if(StringUtils.isNotBlank(createTimeS)){
					travelInfo.setCreateTime(DateUtil.converUTCDate(sdf2.parse(createTimeS)));
				} else {
					travelInfo.setCreateTime(DateUtil.strToDate("1970-01-01"));
				}
				travelInfo.setContent(content);
				travelInfoList.add(travelInfo);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PageIterator<TravelInfo> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(travelInfoList);
		return pageIterator;
	}
	
	public PageIterator<UserInfo> searchUser(String keyword, String city, int pageNo, int pageSize, String orderBy, boolean orderDesc){
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
//			querySb.append("targetCity:").append(city);
			city = city.replace(" ", ",");
			city = city.replace("、", ",");
			city = city.replace("，", ",");
			city = city.replace("。", ",");
			city = city.replace("……", ",");
			city = city.replace(".", ",");
			String[] cityArray = city.split(",");
			if(cityArray.length == 1){
				querySb.append("targetCity:").append(city).append(" or ");
				querySb.append("presentAddress:").append(city).append(" or ");
				querySb.append("previousAddress:").append(city).append(" or ");
				querySb.append("userIntro:").append(city);
			} else {
				querySb.append("( targetCity:").append(cityArray[0]).append(" or ");
				querySb.append("presentAddress:").append(city).append(" or ");
				querySb.append("previousAddress:").append(city).append(" or ");
				querySb.append("userIntro:").append(city);
				for(int i=1; i<=cityArray.length-1; i++){
					if(StringUtils.isNotBlank(cityArray[i])){
						querySb.append(" or ").append("targetCity:").append(cityArray[i]).append(" or ");
						querySb.append("presentAddress:").append(city).append(" or ");
						querySb.append("previousAddress:").append(city).append(" or ");
						querySb.append("userIntro:").append(city);
					}
					if(i == cityArray.length -1){
						querySb.append(")");
					}
				}
			}
		}
		if(StringUtils.isNotBlank(keyword)){
			StringBuilder keywordSb = new StringBuilder("(");
			keywordSb.append("name:").append(keyword).append(" or ");
			keywordSb.append("nickname:").append(keyword).append(" or ");
			keywordSb.append("weiboName:").append(keyword).append(" or ");
			keywordSb.append("wechatName:").append(keyword).append(" or ");
			keywordSb.append("presentAddress:").append(keyword).append(" or ");
			keywordSb.append("previousAddress:").append(keyword).append(" or ");
			keywordSb.append("userIntro:").append(keyword);
			keywordSb.append(")");
			
			if(querySb.length() > 0){
				querySb.append(" and ").append(keywordSb);
			} else {
				querySb.append(keywordSb.substring(1, keywordSb.length() -1 ));
			}
		}
		
		if(querySb.length() <= 0){
			querySb.append(" *:* ");
		}
		querySb.append(" and !uid:1  and !uid:2");
		query.setQuery(querySb.toString());
		query.setFilterQueries("status:2");
		if(StringUtils.isBlank(orderBy)){
			query.setSortField("updateTime", ORDER.desc);
		} else {
			query.setSortField(orderBy, orderDesc ?ORDER.desc:ORDER.asc);
		}
		
		List<UserInfo> userInfoList = null;
		Integer totalCount = 0;
		
		try {
			QueryResponse resp = this.getLryPlannerServers()[0].query(query);
			SolrDocumentList docList = resp.getResults();
			totalCount = (int) docList.getNumFound();	// 命中个数
			Iterator<SolrDocument> docIter = docList.iterator();
			userInfoList = new ArrayList<UserInfo>();
			while(docIter.hasNext()){
				SolrDocument doc = docIter.next();
				Integer uid = (Integer) doc.getFieldValue("uid");
				String name = (String) doc.getFieldValue("name");
				String nickname = (String) doc.getFieldValue("nickname");
				String email = (String) doc.getFieldValue("email");
				String weiboName = (String) doc.getFieldValue("weiboName");
				String wechatName = (String) doc.getFieldValue("wechatName");
				String presentAddress = (String) doc.getFieldValue("presentAddress");
				String previousAddress = (String) doc.getFieldValue("previousAddress");
				String intro = (String) doc.getFieldValue("userIntro");
				String avatar = (String) doc.getFieldValue("avatar");
				Integer viewCntSum = (Integer) doc.getFieldValue("viewCntSum");
				UserInfo userInfo = new UserInfo();
				userInfo.setId(uid);
				userInfo.setName(name);
				userInfo.setEmail(email);
				userInfo.setNickname(nickname);
				userInfo.setPresentAddress(presentAddress);
				userInfo.setPreviousAddress(previousAddress);
				userInfo.setUserIntro(intro);
				userInfo.setWeiboName(weiboName);
				userInfo.setWechatName(wechatName);
				userInfo.setAvatar(avatar);
				userInfo.setViewCntSum(null == viewCntSum ? 0 : viewCntSum);
				userInfoList.add(userInfo);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		PageIterator<UserInfo> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(userInfoList);
		return pageIterator;
	}
	
}
