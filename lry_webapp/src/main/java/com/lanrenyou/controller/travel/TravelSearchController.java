package com.lanrenyou.controller.travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mybatis.framework.core.support.PageIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.user.model.UserFollow;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserFollowService;
import com.lanrenyou.user.service.IUserInfoService;

@Controller
@RequestMapping("/travel/search")
public class TravelSearchController  extends BaseController {
	
	@Autowired
	private ITravelVisitLogService travelVisitLogService;
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserFollowService userFollowService;
	
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
		ModelAndView mav = new ModelAndView("/travel/travel_search");
		mav.addObject("keyword", keyword);
		mav.addObject("city", city);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		PageIterator<TravelInfo> pageIter = solrUtil.searchTravel(keyword.equals("请输入旅游城市或国家") ? "" : keyword, city, null, pageNo, pageSize, "updateTime", true);
		prepareSearchData(pageNo, pageSize, pageIter, mav);
		
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
		prepareSearchData(pageNo, pageSize, pageIter, mav);
		
		return mav;
	}
	
	@RequestMapping("/latest")
	public ModelAndView latestTravel(
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
			){
		if(null == pageNo){
			pageNo = 1;
		}
		if(null == pageSize){
			pageSize = 10;
		}
		ModelAndView mav = new ModelAndView("/travel/travel_latest");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		PageIterator<TravelInfo> pageIter = solrUtil.searchTravel(null, null, null, pageNo, pageSize, "updateTime", true);
		prepareSearchData(pageNo, pageSize, pageIter, mav);
		
		return mav;
	}

	/*
	 * 针对页面显示内容准备数据
	 */
	private void prepareSearchData(Integer pageNo, Integer pageSize, PageIterator<TravelInfo> pageIter,
			ModelAndView mav) {
		
		mav.addObject("pageIter", pageIter);
		mav.addObject("travelInfoList", pageIter.getData());

		Set<Integer> uidSet = new HashSet<Integer>();
		List<Integer> tidList = new ArrayList<Integer>();
		Map<Integer, String> contentMap = new HashMap<Integer, String>();
		for(TravelInfo travelInfo : pageIter.getData()){
			tidList.add(travelInfo.getId());
			uidSet.add(travelInfo.getUid());
			contentMap.put(travelInfo.getId(), travelInfo.getContent());
		}
		
		Map<Integer, Map<String, String>> infoMap = TravelShowUtil.getShowInfoForTravelSearch(contentMap);
		mav.addObject("infoMap", infoMap);
		
		List<Integer> uidList = new ArrayList<Integer>(uidSet);
		Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);
		mav.addObject("userInfoMap", userInfoMap);
		
		Map<Integer, Integer> fansCntMap = userFollowService.getFansCountMapByUidList(uidList);
		mav.addObject("fansCntMap", fansCntMap);
		Map<Integer, Integer> starCntMap = userFollowService.getStarCountMapByUidList(uidList);
		mav.addObject("starCntMap", starCntMap);
		
		Map<Integer, Integer> userPublishedTravelCntMap = travelInfoService.getPublishedTravelCntMapByUidList(uidList);
		mav.addObject("userPublishedTravelCntMap", userPublishedTravelCntMap);
		
		Map<Integer, Integer> travelVisitCntMap = getTravelVisitCntByTidList(tidList);
		mav.addObject("travelVisitCntMap", travelVisitCntMap);
		
		if(null != this.getLoginUser()){
			PageIterator<UserFollow> followPageIter = userFollowService.pageQueryStarByUid(this.getLoginUser().getId(), 1, 100);
			if(null != followPageIter && null != followPageIter.getData()){
				Map<Integer, Integer> userStarMap = new HashMap<Integer, Integer>();
				for(UserFollow uf : followPageIter.getData()){
					userStarMap.put(uf.getStarUid(), 1);
				}
				mav.addObject("userStarMap", userStarMap);
			}
		}
	}
			
}
