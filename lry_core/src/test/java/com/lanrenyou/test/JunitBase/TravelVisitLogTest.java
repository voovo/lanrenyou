package com.lanrenyou.test.JunitBase;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.lanrenyou.travel.service.ITravelVisitLogService;


public class TravelVisitLogTest extends Junit4Base{
	
	@Autowired
    private ITravelVisitLogService service;
	
	private Gson gson = new Gson();
	
	@Test
	public void testQueryDB(){
//		int cnt = service.getVisitCountByTid(1);
//		logger.info("############### query count:{}", cnt);
		
		List<Integer> tidList = new ArrayList<Integer>();
		tidList.add(1);
		tidList.add(2);
		
		Map<Integer, Integer> map = service.getVisitCountMapByTidList(tidList);
		logger.info("####################### query result:{}", gson.toJson(map));
	}
}