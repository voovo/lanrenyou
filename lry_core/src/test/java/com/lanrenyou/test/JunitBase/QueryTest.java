package com.lanrenyou.test.JunitBase;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.lanrenyou.travel.service.IIndexTravelService;


public class QueryTest extends Junit4Base{
	
	@Autowired
    private IIndexTravelService service;
	
	private Gson gson = new Gson();
	
	@Test
	public void testQueryDB(){
		service.getIndexTravel(24);
	}
	// test clone commit
}