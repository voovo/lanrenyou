package com.lanrenyou.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.controller.travel.TravelShowUtil;
import com.lanrenyou.letter.migrate.Migrate;
import com.lanrenyou.travel.model.IndexTravel;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.IIndexTravelService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;

@Controller
@RequestMapping("")
public class IndexController  extends BaseController {
	
	@Autowired
	private IIndexTravelService indexTravelService;
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value={"/index", "/", ""})
	public ModelAndView toAddPage(){
		ModelAndView mav =  new ModelAndView("/index");
		List<IndexTravel> indexTravelList = indexTravelService.getIndexTravel(24);
		List<Integer> tidList = new ArrayList<Integer>();
		for(IndexTravel it : indexTravelList){
			tidList.add(it.getTid());
		}
		mav.addObject("tidList", tidList);
		
		Map<Integer, TravelInfo> travelInfoMap = travelInfoService.getTravelInfoMapByTidList(tidList);
		
		if(null == travelInfoMap){
			return mav;
		}
		mav.addObject("travelInfoMap", travelInfoMap);
		
		Set<Integer> uidSet = new HashSet<Integer>();
		Map<Integer, String> contentMap = new HashMap<Integer, String>();
		for(Integer tid : tidList){
			TravelInfo travelInfo = travelInfoMap.get(tid);
			uidSet.add(travelInfo.getUid());
			contentMap.put(travelInfo.getId(), travelInfo.getContent());
		}
		
		Map<Integer, Map<String, String>> infoMap = TravelShowUtil.getShowInfoForTravelSearch(contentMap);
		mav.addObject("infoMap", infoMap);
		
		List<Integer> uidList = new ArrayList<Integer>(uidSet);
		Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);
		mav.addObject("userInfoMap", userInfoMap);
		
		return mav;
	}

}
