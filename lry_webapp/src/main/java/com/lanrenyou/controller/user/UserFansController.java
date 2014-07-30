package com.lanrenyou.controller.user;

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
import com.lanrenyou.user.model.UserFollow;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserFollowService;
import com.lanrenyou.user.service.IUserInfoService;

@Controller
@RequestMapping("/user/{uid:[\\d]+}/fans")
public class UserFansController  extends BaseController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserFollowService userFollowService;
	
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
		ModelAndView mav = new ModelAndView("/user/user_fans");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("userInfo", this.getCurrentUser());
		
		PageIterator<UserFollow> pageIter = userFollowService.pageQueryFansByUid(this.getCurrentUser().getId(), pageNo, pageSize);
		mav.addObject("pageIter", pageIter);
		
		List<Integer> uidList = new ArrayList<Integer>();
		Set<Integer> uidSet = new HashSet<Integer>();
		if(null != pageIter && null != pageIter.getData()){
			mav.addObject("fansCnt", pageIter.getTotalCount());
			for(UserFollow userFollow : pageIter.getData()){
				uidSet.add(userFollow.getFansUid());
			}
			uidList = new ArrayList<Integer>(uidSet);
			mav.addObject("uidList", uidList);
		}
		
		Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);
		mav.addObject("userInfoMap", userInfoMap);
		
		PageIterator<UserFollow> starPageIter = userFollowService.pageQueryStarByUid(this.getCurrentUser().getId(), pageNo, pageSize);
		if(null != starPageIter && null != starPageIter.getData()){
			mav.addObject("starCnt", starPageIter.getTotalCount());
			Map<Integer, Integer> starMap = new HashMap<Integer, Integer>();
			for(UserFollow uf : starPageIter.getData()){
				starMap.put(uf.getStarUid(), 1);
			}
			mav.addObject("starMap", starMap);
		}
		
		return mav;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	@ResponseBody
	public String addFollow(){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == this.getLoginUser()){
			map.put("status", "n");
			map.put("info", "请先登录");
			return gson.toJson(map);
		}
		
		if(this.getLoginUser().getId().intValue() == this.getCurrentUser().getId().intValue()){
			map.put("status", "n");
			map.put("info", "不得关注自己");
			return gson.toJson(map);
		}
		
		boolean isFollowed = userFollowService.isFollowed(this.getLoginUser().getId(), this.getCurrentUser().getId());
		if(isFollowed){
			map.put("status", "y");
			map.put("info", "用户已经关注过此用户");
			return gson.toJson(map);
		} else {
			int result = userFollowService.addUserFollow(this.getLoginUser().getId(), this.getCurrentUser().getId());
			if(result > 0){
				map.put("status", "y");
				map.put("info", "关注成功");
				return gson.toJson(map);
			} else {
				map.put("status", "n");
				map.put("info", "系统忙，请稍后重试");
				return gson.toJson(map);
			}
		}
	}
	
	@RequestMapping(value="/del", method=RequestMethod.GET)
	@ResponseBody
	public String removeFollow(){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == this.getLoginUser()){
			map.put("status", "n");
			map.put("info", "请先登录");
			return gson.toJson(map);
		}
		
		boolean isFollowed = userFollowService.isFollowed(this.getLoginUser().getId(), this.getCurrentUser().getId());
		if(isFollowed){
			int result = userFollowService.removeUserFollow(this.getLoginUser().getId(), this.getCurrentUser().getId());
			if(result > 0){
				map.put("status", "y");
				map.put("info", "取消关注成功");
				return gson.toJson(map);
			} else {
				map.put("status", "n");
				map.put("info", "系统忙，请稍后重试");
				return gson.toJson(map);
			}
		} else {
			map.put("status", "y");
			map.put("info", "用户未关注过此用户");
			return gson.toJson(map);
		}
	}
}
