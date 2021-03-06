package com.lanrenyou.controller.travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TravelShowUtil {
	
	private static Gson gson = new Gson();
	
	protected static Logger logger = LoggerFactory.getLogger(TravelShowUtil.class);
	
	public static List<Map<String, String>> getShowInfoForTravelDetail(String content){
		if(StringUtils.isBlank(content)){
			return null;
		}
		
		List<Map<String, String>> list = null;
		try{
			list = gson.fromJson(content, new TypeToken<List<Map<String, String>>>(){}.getType());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(content);
		}
		if(null != list){
			for(Map<String, String> map : list){
				if(null != map){
					String img = map.get("src");
					if(StringUtils.isNotBlank(img) && (img.contains("wp-content/uploads") || img.contains("img.lanrenyou.com"))){
						if(img.endsWith("_s.jpg")){
							img = img.replace("_s.jpg", "_l.jpg");
							map.put("src", img);
						}else if(!img.endsWith("_s.jpg")){
							img = img.replace(".jpg", "_l.jpg");
							map.put("src", img);
						}
					}
				}
			}
		}
		return list;
	}
	
	public static List<Map<String, String>> getShowInfoForTravelEdit(String content){
		if(StringUtils.isBlank(content)){
			return null;
		}
		
		List<Map<String, String>> list = null;
		try{
			list = gson.fromJson(content, new TypeToken<List<Map<String, String>>>(){}.getType());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(content);
		}
		if(null != list){
			for(Map<String, String> map : list){
				if(null != map){
					String img = map.get("src");
					map.put("src", img);
				}
			}
		}
		return list;
	}
	
	/*
	 * 取出单个游记内容中的封面与头条信息
	 */
	public static Map<String, String> getShowInfoForTravelSearch(String content){
		if(StringUtils.isBlank(content)){
			return null;
		}
		
		List<Map<String, String>> list = null;
		try{
			list = gson.fromJson(content, new TypeToken<List<Map<String, String>>>(){}.getType());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(content);
		}
		if(null != list && list.size() >= 1){
			Map<String, String> map = list.get(0);
			if(null != map){
				String img = map.get("src");
				if(StringUtils.isNotBlank(img) && (img.contains("wp-content/uploads") || img.contains("img.lanrenyou.com"))){
					if(!img.endsWith("_s.jpg")){
						img = img.replace(".jpg", "_s.jpg");
						map.put("src", img);
					} else {
						img = img.replace("_s.jpg", "_l.jpg");
						map.put("src", img);
					}
				}
			}
			return map;
		}
		return null;
	}
	
	/*
	 * 批量取出多个游记内容中的封面与头条信息
	 */
	public static Map<Integer, Map<String, String>> getShowInfoForTravelSearch(Map<Integer, String> contentMap){
		if(null == contentMap || contentMap.size() <= 0){
			return null;
		}
		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String, String>>();
		for(Integer key : contentMap.keySet()){
			String content = contentMap.get(key);
			if(StringUtils.isBlank(content)){
				continue;
			}
			List<Map<String, String>> list = null;
			try{
				list = gson.fromJson(content, new TypeToken<List<Map<String, String>>>(){}.getType());
			}catch(Exception e){
				e.printStackTrace();
				logger.error(content);
			}
			if(null != list && list.size() >= 1){
				Map<String, String> map = list.get(0);
				if(null != map){
					String img = map.get("src");
					if(StringUtils.isNotBlank(img) && (img.contains("wp-content/uploads") || img.contains("img.lanrenyou.com"))){
						if(!img.endsWith("_s.jpg")){
							img = img.replace(".jpg", "_l.jpg");
							map.put("src", img);
						} else {
							img = img.replace("_s.jpg", "_l.jpg");
							map.put("src", img);
						}
					}
				}
				returnMap.put(key, map);
			}
		}
		return returnMap;
	}
	
	public static Map<Integer, Map<String, String>> getShowInfoForUserIndex(Map<Integer, String> contentMap){
		if(null == contentMap || contentMap.size() <= 0){
			return null;
		}
		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String, String>>();
		for(Integer key : contentMap.keySet()){
			String content = contentMap.get(key);
			if(StringUtils.isBlank(content)){
				continue;
			}
			List<Map<String, String>> list = null;
			try{
				list = gson.fromJson(content, new TypeToken<List<Map<String, String>>>(){}.getType());
			}catch(Exception e){
				e.printStackTrace();
				logger.error(content);
			}
			if(null != list && list.size() >= 1){
				Map<String, String> map = list.get(0);
				returnMap.put(key, map);
			}
		}
		return returnMap;
	}
	
	public static void main(String[] args){
		String content = "[{\"src\":\"http://img.lanrenyou.com/2014/07/16/1_s.jpg\" , \"info\" : \"11111111111\"},{\"src\":\"http://img.lanrenyou.com/2014/07/16/2_s.jpg\" , \"info\" : \"22222222222\"},{\"src\":\"http://img.lanrenyou.com/2014/07/16/3_s.jpg\" , \"info\" : \"333333333333\"}]";
//		String content = "[{\"src\":\"http://img.lanrenyou.com/2014/07/16/181526206_s.jpg\" , \"info\" : \"asas\"}]";
		Map<String, String> map = getShowInfoForTravelSearch(content);
		System.out.println(map.get("src"));
		System.out.println(map.get("info"));
	}
}
