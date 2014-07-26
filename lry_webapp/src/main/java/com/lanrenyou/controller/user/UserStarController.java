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
@RequestMapping("/user/{uid:[\\d]+}/star")
public class UserStarController  extends BaseController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserFollowService userFollowService;
	
	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
			) {
		if(null == this.getLoginUser()){
			return to404();
		}
		if(this.getLoginUser().getId().intValue() != this.getCurrentUser().getId().intValue()){
			return toError("没有权限访问此页面");
		}
		if(null == pageNo){
			pageNo = 1;
		}
		if(null == pageSize){
			pageSize = 10;
		}
		ModelAndView mav = new ModelAndView("/user/user_star");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("userInfo", this.getLoginUser());
		
		PageIterator<UserFollow> pageIter = userFollowService.pageQueryStarByUid(this.getLoginUser().getId(), pageNo, pageSize);
		
		List<Integer> uidList = new ArrayList<Integer>();
		Set<Integer> uidSet = new HashSet<Integer>();
		if(null != pageIter && null != pageIter.getData()){
			mav.addObject("pageIter", pageIter);
			mav.addObject("starCnt", pageIter.getTotalCount());
			for(UserFollow userFollow : pageIter.getData()){
				uidSet.add(userFollow.getFansUid());
			}
			uidList = new ArrayList<Integer>(uidSet);
			mav.addObject("uidList", uidList);
		}
		
		Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);
		mav.addObject("userInfoMap", userInfoMap);
		
		PageIterator<UserFollow> fansPageIter = userFollowService.pageQueryFansByUid(this.getLoginUser().getId(), 1, 100);
		if(null != fansPageIter && null != fansPageIter.getData()){
			mav.addObject("fansCnt", fansPageIter.getTotalCount());
			Map<Integer, Integer> fansMap = new HashMap<Integer, Integer>();
			for(UserFollow uf : fansPageIter.getData()){
				fansMap.put(uf.getFansUid(), 1);
			}
			mav.addObject("fansMap", fansMap);
		}
		
		return mav;
	}
	
}
