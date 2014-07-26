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
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserFollowService;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;

@Controller
@RequestMapping("/user/search")
public class UserSearchController  extends BaseController {
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserFollowService userFollowService;
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	@Autowired
	private SolrUtil solrUtil;
	
	@RequestMapping("/list")
	public ModelAndView search(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "city", required = false, defaultValue = "") String city,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
			) {
		if(null == pageNo){
			pageNo = 1;
		}
		if(null == pageSize){
			pageSize = 10;
		}
		ModelAndView mav = new ModelAndView("/user/user_search");
		mav.addObject("keyword", keyword);
		mav.addObject("city", city);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		PageIterator<UserInfo> pageIter = solrUtil.searchUser(keyword.equals("请输入旅游城市或国家") ? "" : keyword, city,pageNo, pageSize, "updateTime", true);
		List<Integer> uidList = new ArrayList<Integer>();
		if(null != pageIter && null != pageIter.getData()){
			mav.addObject("userInfoList", pageIter.getData());
			for(UserInfo userInfo : pageIter.getData()){
				uidList.add(userInfo.getId());
			}
		}
		
		Map<Integer, Integer> fansCntMap = userFollowService.getFansCountMapByUidList(uidList);
		mav.addObject("fansCntMap", fansCntMap);
		Map<Integer, Integer> starCntMap = userFollowService.getStarCountMapByUidList(uidList);
		mav.addObject("starCntMap", starCntMap);
		
		Map<Integer, Integer> userPublishedTravelCntMap = travelInfoService.getPublishedTravelCntMapByUidList(uidList);
		mav.addObject("userPublishedTravelCntMap", userPublishedTravelCntMap);
		
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
		
		return mav;
	}
	
	@RequestMapping("/publishedTravels")
	public ModelAndView getUserPublishedTravels(@RequestParam(value = "uid", required = true) Integer uid){
		ModelAndView mav = new ModelAndView("/user/user_search_published_travels");
		mav.addObject("uid", uid);
		PageIterator<TravelInfo> pageIter = solrUtil.searchTravel(null, null, uid, 1, 4, "updateTime", true);
		if(null != pageIter && null != pageIter.getData() && pageIter.getData().size() > 0){
			mav.addObject("travelInfoList", pageIter.getData());
			TravelInfo travelInfo = pageIter.getData().get(0);
			mav.addObject("firstTid", travelInfo.getId());
			TravelContent travelContent = travelContentService.getTravelContentByTid(travelInfo.getId());
			if(null != travelContent){
				Map<String, String> contentMap = TravelShowUtil.getShowInfoForTravelSearch(travelContent.getContent());
				if(null != contentMap && StringUtils.isNotBlank(contentMap.get("src"))){
					mav.addObject("firstImg", contentMap.get("src"));
				}
			}
			
		}
		return mav;
	}
	
	@RequestMapping("/hot")
	public ModelAndView hotTravel(
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
			){
		if(null == pageNo){
			pageNo = 1;
		}
		if(null == pageSize){
			pageSize = 10;
		}
		ModelAndView mav = new ModelAndView("/travel/travel_hot");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		PageIterator<TravelInfo> pageIter = solrUtil.searchTravel(null, null, null, pageNo, pageSize, "viewCnt", true);
		
		return mav;
	}
			
}
