package com.lanrenyou.controller.travel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.model.TravelVisitLog;

@Controller
@RequestMapping("/travel/search")
public class TravelSearchController  extends BaseController {
	
	@Autowired
	private ITravelVisitLogService travelVisitLogService;
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@RequestMapping("/toPage")
	public String travelSearch(){
		// TODO zhangpj
		return null;
	}
	
	/*
	 * 批量获取游记信息
	 */
	private Map<Integer, TravelInfo> getTravelInfoMapByTidList(List<Integer> tidList){
		if(null == tidList || tidList.size() <= 0){
			return null;
		}
		return travelInfoService.getTravelInfoMapByTidList(tidList);
	}
	
	/*
	 * 批量获取游记的浏览数
	 */
	private Map<Integer, Integer> getTravelVisitCntByTidList(List<Integer> tidList){
		if(null == tidList || tidList.size() <= 0){
			return null;
		}
		return travelVisitLogService.getVisitCountMapByTidList(tidList);
	}
}
