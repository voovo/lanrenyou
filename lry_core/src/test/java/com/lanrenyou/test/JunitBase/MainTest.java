package com.lanrenyou.test.JunitBase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanrenyou.travel.service.IIndexTravelService;


public class MainTest{
	
	
	public static void main(String[] args){
		Gson gson = new Gson();
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		Map<String, String> map_1 = new HashMap<String, String>();
//		map_1.put("src", "http://img.lanrenyou.com/2014/08/04/100619081_s.jpg");
//		map_1.put("info", "位于加勒比海的大安的列斯群岛东部，包括波多黎各岛及别克斯、库莱布拉等小岛。Castillo San Cristobal是老城圣胡安的一座古堡，红色城墙衬着蓝天碧海。");
//		Map<String, String> map_1 = new HashMap<String, String>();
//		Map<String, String> map_1 = new HashMap<String, String>();
		
		String content = "[{\"src\":\"http://img.lanrenyou.com/2014/08/04/100619081_s.jpg\" , \"info\" : \"位于加勒比海的大安的列斯群岛东部，包括波多黎各岛及别克斯、库莱布拉等小岛。Castillo San Cristobal是老城圣胡安的一座古堡，红色城墙衬着蓝天碧海。\"},{\"src\":\"http://img.lanrenyou.com/2014/08/04/101031895_s.jpg\" , \"info\" : \"五彩斑斓的各种房屋是圣胡安老城的特色，徜徉其中，仿佛走在油画里一样。联合国于1981年将圣胡安列入世界文化与自然遗产的名录。\"}]";
		List<Map<String, String>> list = gson.fromJson(content, new TypeToken<List<Map<String, String>>>(){}.getType());
	}
}