package com.lanrenyou.controller.travel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.travel.service.ITravelCollectService;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import com.lanrenyou.travel.model.TravelCollect;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelVisitLog;

@Controller
@RequestMapping("/travel/{tid:[\\d]+}")
public class TravelIndexController  extends BaseController {
	
	@Autowired
	private ITravelVisitLogService travelVisitLogService;

	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private ITravelCollectService travelCollectService;
	
	@RequestMapping(value="/visit", method=RequestMethod.GET)
	@ResponseBody
	public String addVisit(){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == getCurrentTravel()){
			map.put("status", "n");
			map.put("info", "没有获取当前游记");
			return gson.toJson(map);
		}
		TravelVisitLog visitLog = new TravelVisitLog();
		visitLog.setTid(this.getCurrentTravel().getId());
		if(null != this.getLoginUser()){
			visitLog.setUid(this.getLoginUser().getId());
		} else {
			visitLog.setUid(0);
		}
		int result = travelVisitLogService.addTravelVisitLog(visitLog);
		if(result > 0){
			map.put("status", "y");
			map.put("info", "记录成功");
		} else {
			map.put("status", "n");
			map.put("info", "系统正忙，请稍后再试");
		}
		return gson.toJson(map);
	}
	
	@RequestMapping(value={""}, method=RequestMethod.GET)
	public ModelAndView detail(){
		if(null == this.getCurrentTravel()){
			return to404();
		}
		
		ModelAndView mav = new ModelAndView("/travel/travel_detail");
		mav.addObject("travelInfo", this.getCurrentTravel());
		TravelContent travelContent = travelContentService.getTravelContentByTid(this.getCurrentTravel().getId());
		List<Map<String, String>> contentList = TravelShowUtil.getShowInfoForTravelDetail(travelContent.getContent());
		mav.addObject("contentList", contentList);
		
		int visitCnt = travelVisitLogService.getVisitCountByTid(this.getCurrentTravel().getId());
		mav.addObject("visitCnt", visitCnt);
		
		int collectCnt = travelCollectService.getCollectCntByTid(this.getCurrentTravel().getId());
		mav.addObject("collectCnt", collectCnt);
		
		return mav;
	}
	
	@RequestMapping(value="/collect", method=RequestMethod.GET)
	@ResponseBody
	public String addCollect(){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == getCurrentTravel()){
			map.put("status", "n");
			map.put("info", "没有获取当前游记信息");
			return gson.toJson(map);
		}
		if(null == getLoginUser()){
			map.put("status", "n");
			map.put("info", "没有获取当前用户信息");
			return gson.toJson(map);
		}
		TravelCollect travelCollect = new TravelCollect();
		travelCollect.setUid(getLoginUser().getId());
		travelCollect.setTid(this.getCurrentTravel().getId());
		int result = travelCollectService.addTravelCollect(travelCollect);
		if(result > 0){
			map.put("status", "y");
			map.put("info", "收藏成功");
		} else {
			map.put("status", "n");
			map.put("info", "系统正忙，请稍后再试");
		}
		return gson.toJson(map);
	}
	
	@RequestMapping(value="/uncollect", method=RequestMethod.GET)
	@ResponseBody
	public String delCollect(){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == getCurrentTravel()){
			map.put("status", "n");
			map.put("info", "没有获取当前游记信息");
			return gson.toJson(map);
		}
		if(null == getLoginUser()){
			map.put("status", "n");
			map.put("info", "没有获取当前用户信息");
			return gson.toJson(map);
		}
		int result = travelCollectService.deleteByUidTid(getLoginUser().getId(), this.getCurrentTravel().getId());
		if(result > 0){
			map.put("status", "y");
			map.put("info", "取消收藏成功");
		} else {
			map.put("status", "n");
			map.put("info", "系统正忙，请稍后再试");
		}
		return gson.toJson(map);
	}
}
