package com.lanrenyou.controller.travel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.travel.service.ITravelCollectService;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.service.ITravelInfoStatService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import com.lanrenyou.travel.service.impl.TravelInfoStatServiceImpl;
import com.lanrenyou.travel.enums.TravelInfoIsEliteEnum;
import com.lanrenyou.travel.enums.TravelInfoIsTopEnum;
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.TravelCollect;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfoStat;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.model.TravelVisitLog;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;

@Controller
@RequestMapping("/travel/{tid:[\\d]+}")
public class TravelIndexController  extends BaseController {
	
	@Autowired
	private ITravelVisitLogService travelVisitLogService;

	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private ITravelCollectService travelCollectService;
	
	@Autowired
	private ITravelInfoStatService travelInfoStatService;
	
	@Autowired
	private ITravelInfoService travelInfoService;

	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	@Autowired
	private SolrUtil solrUtil;
	
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
		
		TravelInfoStat travelStat = travelInfoStatService.getTravelInfoStatByTid(this.getCurrentTravel().getId());
		mav.addObject("visitCnt", travelStat == null ? 0 : travelStat.getViewCnt());
		mav.addObject("likeCnt", travelStat == null ? 0 : travelStat.getLikeCnt());
		
		int collectCnt = travelCollectService.getCollectCntByTid(this.getCurrentTravel().getId());
		mav.addObject("collectCnt", collectCnt);
		
		UserInfo userInfo = userInfoService.getUserInfoByUid(this.getCurrentTravel().getUid());
		mav.addObject("userInfo", userInfo);
		
		UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(this.getCurrentTravel().getUid());
		if(null != userPlanner && StringUtils.isNotBlank(userPlanner.getTargetCity())){
			String[] plannCities = userPlanner.getTargetCity().split(",");
			mav.addObject("plannCities", plannCities);
		}
		
		PageIterator<TravelInfo> pageIter = solrUtil.searchTravel(null, null, this.getCurrentTravel().getUid(), 1, 3, "updateTime", true);
		if(null != pageIter && null != pageIter.getData()){
			mav.addObject("userTravelList", pageIter.getData());
		}
		
		return mav;
	}
	
	@RequestMapping(value="/similarTravel", method=RequestMethod.GET)
	public ModelAndView similarTravel(){
		if(null == this.getCurrentTravel()){
			return to404();
		}
		
		ModelAndView mav = new ModelAndView("/travel/travel_detail_similar");
		mav.addObject("travelInfo", this.getCurrentTravel());
		mav.addObject("city", this.getCurrentTravel().getCity());
		PageIterator<TravelInfo> pageIter = solrUtil.searchTravel(null, this.getCurrentTravel().getCity(), null, 1, 6, "updateTime", true);
		if(null != pageIter && null != pageIter.getData() && pageIter.getData().size() > 0){
			mav.addObject("travelInfoList", pageIter.getData());
		}
		
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
	
	@RequestMapping(value={"toEdit"}, method=RequestMethod.GET)
	public ModelAndView toEdit(){
		if(null == this.getCurrentTravel()){
			return to404();
		}
		
		ModelAndView mav = new ModelAndView("/travel/travel_edit");
		mav.addObject("travelInfo", this.getCurrentTravel());
		TravelContent travelContent = travelContentService.getTravelContentByTid(this.getCurrentTravel().getId());
		List<Map<String, String>> contentList = TravelShowUtil.getShowInfoForTravelDetail(travelContent.getContent());
		mav.addObject("contentList", contentList);
		
		return mav;
	}
	
	@RequestMapping(value={"edit"}, method=RequestMethod.GET)
	@ResponseBody
	public String doEdit(
			@RequestParam(value = "tid", required=true, defaultValue="") Integer tid,
			@RequestParam(value = "title", required=true, defaultValue="") String title,
			@RequestParam(value = "area", required=true, defaultValue="") String area,
			@RequestParam(value = "imgs", required=true, defaultValue="") String imgs
		){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == this.getCurrentTravel()){
			map.put("status", "n");
			map.put("info", "找不到当前游记信息");
			return gson.toJson(map);
		}
		
		if(null == tid || tid != this.getCurrentTravel().getId().intValue()){
			map.put("status", "n");
			map.put("info", "编辑的不是当前游记");
			return gson.toJson(map);
		}
		
		TravelInfo travelInfo = travelInfoService.getTravelInfoById(tid);
		travelInfo.setTitle(title.trim());
		travelInfo.setCity(area.trim());
		travelInfo.setIsElite(TravelInfoIsEliteEnum.NO.getValue());
		travelInfo.setIsTop(TravelInfoIsTopEnum.NO.getValue());
		travelInfo.setStatus(TravelInfoStatusEnum.NORMAL.getValue());
		travelInfo.setUpdateUid(this.getLoginUser().getId());
		travelInfo.setUpdateIp(this.getRemoteAddr());
		int result = travelInfoService.updateTravelInfo(travelInfo);
		if(result < 1){
			map.put("status", "n");
			map.put("info", "更新数据失败");
			return gson.toJson(map);
		}
		
		TravelContent travelContent = travelContentService.getTravelContentByTid(tid);
		travelContent.setContent(imgs);
		result = travelContentService.addTravelContent(travelContent);
		
		if(result < 1){
			map.put("status", "n");
			map.put("info", "更新游记内容失败，请稍后重试");
			return gson.toJson(map);
		}
		
		map.put("status", "y");
		map.put("info", "更新游记成功");
		return gson.toJson(map);
	}
}