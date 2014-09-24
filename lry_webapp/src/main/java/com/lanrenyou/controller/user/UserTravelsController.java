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
import com.lanrenyou.travel.service.ITravelInfoStatService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import com.lanrenyou.travel.service.ITravelCollectService;
import com.lanrenyou.travel.model.TravelCollect;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.model.TravelInfoStat;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserFollowService;
import com.lanrenyou.user.service.IUserInfoService;

@Controller
@RequestMapping("/user/{uid:[\\d]+}/travelList")
public class UserTravelsController  extends BaseController {
	
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
	private ITravelInfoStatService travelInfoStatService;
	
	@Autowired
	private SolrUtil solrUtil;
	
	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
			) {
		if(null == this.getLoginUser() || this.getLoginUser().getId().intValue() != this.getCurrentUser().getId()){
			return to404();
		}
		if(null == pageNo){
			pageNo = 1;
		}
		if(null == pageSize){
			pageSize = 10;
		}
		ModelAndView mav = new ModelAndView("/user/user_travels");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("userInfo", this.getCurrentUser());
		PageIterator<TravelInfo> pageIter = travelInfoService.pageQueryTravelInfoForPlanner(this.getLoginUser().getId().intValue(), pageNo, pageSize);
		List<Integer> tidList = new ArrayList<Integer>();
		Set<Integer> uidSet = new HashSet<Integer>();
		uidSet.add(this.getCurrentUser().getId());
		if(null != pageIter && null != pageIter.getData()){
			mav.addObject("pageIter", pageIter);
			for(TravelInfo travelInfo : pageIter.getData()){
				tidList.add(travelInfo.getId());
			}
			mav.addObject("tidList", tidList);
			mav.addObject("travelInfoList", pageIter.getData());
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
		
		List<Integer> uidList = new ArrayList<Integer>(uidSet);
		Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);
		mav.addObject("userInfoMap", userInfoMap);
		
		Map<Integer, Integer> travelVisitCntMap = getTravelVisitCntByTidList(tidList);
		mav.addObject("travelVisitCntMap", travelVisitCntMap);
		
		return mav;
	}
	
	/*
	 * 批量获取游记的浏览数
	 */
	private Map<Integer, Integer> getTravelVisitCntByTidList(List<Integer> tidList){
		if(null == tidList || tidList.size() <= 0){
			return null;
		}
		Map<Integer, TravelInfoStat> statMap = travelInfoStatService.getTravelInfoStatMapByTidList(tidList);
		if(null == statMap){
			return null;
		}
		Map<Integer, Integer> visitCntMap = new HashMap<Integer, Integer>();
		for(Integer tid : statMap.keySet()){
			TravelInfoStat stat = statMap.get(tid);
			if(null != stat){
				visitCntMap.put(tid, stat.getViewCnt()==null?0:stat.getViewCnt());
			} else {
				visitCntMap.put(tid, 0);
			}
		}
		return visitCntMap;
	}
			
}
