package com.lanrenyou.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.lanrenyou.controller.travel.TravelShowUtil;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import com.lanrenyou.travel.service.ITravelCollectService;
import com.lanrenyou.travel.model.TravelCollect;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserFollowService;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;

@Controller
@RequestMapping("/user/{uid:[\\d]+}")
public class UserIndexController  extends BaseController {
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private ITravelCollectService travelCollectService;
	
	@Autowired
	private ITravelVisitLogService travelVisitLogService;
	
	@Autowired
	private IUserFollowService userFollowService;
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	@Autowired
	private SolrUtil solrUtil;
	
	@RequestMapping("")
	public ModelAndView index(
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
			) {
		if(null == pageNo){
			pageNo = 1;
		}
		if(null == pageSize){
			pageSize = 10;
		}
		ModelAndView mav = new ModelAndView("/user/user_index");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("userInfo", this.getCurrentUser());
		PageIterator<TravelInfo> pageIter = travelInfoService.pageQueryTravelInfoByUid(this.getCurrentUser().getId(), pageNo, pageSize);
		mav.addObject("pageIter", pageIter);
		List<Integer> tidList = new ArrayList<Integer>();
		if(null != pageIter && null != pageIter.getData()){
			Map<Integer, TravelInfo> travelInfoMap = new HashMap<Integer, TravelInfo>();
			for(TravelInfo travelInfo : pageIter.getData()){
				tidList.add(travelInfo.getId());
				travelInfoMap.put(travelInfo.getId(), travelInfo);
			}
			mav.addObject("tidList", tidList);
			mav.addObject("travelInfoMap",travelInfoMap);
		}
		
		List<TravelContent> travelContentList = travelContentService.getTravelContentListByTidList(tidList);
		Map<Integer, String> contentMap = new HashMap<Integer, String>();
		if(null != travelContentList && travelContentList.size() > 0){
			for(TravelContent content : travelContentList){
				contentMap.put(content.getTid(), content.getContent());
			}
		}
		
		Map<Integer, Map<String, String>> infoMap = TravelShowUtil.getShowInfoForTravelSearch(contentMap);
		mav.addObject("infoMap", infoMap);
		
		Map<Integer, Integer> travelVisitCntMap = getTravelVisitCntByTidList(tidList);
		mav.addObject("travelVisitCntMap", travelVisitCntMap);
		
		List<Integer> uidList = new ArrayList<Integer>();
		uidList.add(this.getCurrentUser().getId());
		Map<Integer, Integer> fansMap = userFollowService.getFansCountMapByUidList(uidList);
		if(null != fansMap && null != fansMap.get(this.getCurrentUser().getId())){
			mav.addObject("fansCount", fansMap.get(this.getCurrentUser().getId()));
		}
		
		Map<Integer, UserPlanner> userPlannerMap = userPlannerService.getUserPlannerMapByUidList(uidList);
		if(null != userPlannerMap && userPlannerMap.size() > 0){
			Map<Integer, String[]> targetCityMap = new HashMap<Integer, String[]>();
			for(Integer uid : userPlannerMap.keySet()){
				UserPlanner userPlanner = userPlannerMap.get(uid);
				if(null != userPlanner && StringUtils.isNotBlank(userPlanner.getTargetCity())){
					targetCityMap.put(uid, userPlanner.getTargetCity().split(","));
				}
			}
			mav.addObject("targetCityMap", targetCityMap);
		}
		
		if(null != this.getLoginUser()){
			PageIterator<TravelCollect> collectPageIter = travelCollectService.pageQueryTravelCollectByUid(this.getLoginUser().getId(), 1, 100);
			if(null != collectPageIter && null != collectPageIter.getData()){
				Map<Integer, Integer> collectTidMap = new HashMap<Integer, Integer>();
				for(TravelCollect collect : collectPageIter.getData()){
					collectTidMap.put(collect.getTid(), 1);
				}
				mav.addObject("collectTidMap", collectTidMap);
			}
		}
		
		return mav;
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
	
	@RequestMapping("/lastTravels")
	public ModelAndView getLastTravles(){
		ModelAndView mav = new ModelAndView("/user/user_lastTravels");
		PageIterator<TravelInfo> pageIter = solrUtil.searchTravel(null, null, this.getCurrentUser().getId(), 1, 2, "updateTime", true);
		if(null != pageIter){
			mav.addObject("travelInfoList", pageIter.getData());
		}
		return mav;
	}
}
