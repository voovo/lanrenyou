package com.lanrenyou.admin.controller.audit;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.user.enums.UserInfoStatusEnum;
import com.lanrenyou.user.enums.UserInfoTypeEnum;
import com.lanrenyou.user.enums.UserPlannerStatusEnum;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;
import com.lanrenyou.util.MailUtil;
import com.lanrenyou.util.freemarker.FreemarkerUtil;


@Controller
@RequestMapping("/audit/planner")
public class AuditPlannerController extends BaseController {
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value="/list")
	public ModelAndView plannerList(
			@RequestParam(value="queryUid",required=false, defaultValue="-1") Integer uid,
			@RequestParam(value="queryStatus",required=false, defaultValue="-1") Integer status,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="10") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/audit/planner_list");
		
		if(null == status ){
			status = -1;
		}
		if(null == uid){
			uid = -1;
		}
		PageIterator<UserPlanner> pageIter = userPlannerService.pageQueryByUidStatus(uid, status, pageNo, pageSize);
		mav.addObject("pageIter", pageIter);
		if(null != pageIter && null != pageIter.getData()){
			List<Integer> uidList = new ArrayList<Integer>();
			for(UserPlanner up : pageIter.getData()){
				uidList.add(up.getUid());
			}
			if(uidList.size() > 0){
				Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);
				mav.addObject("userInfoMap", userInfoMap);
			}
		}
		return mav;
	}

	@RequestMapping(value="/pass")
	public String plannerPass(@RequestParam(value="uid", required=true) Integer uid){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == uid){
			map.put("status", "n");
			map.put("info", "未指定UID");
			return gson.toJson(map);
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if(null == userInfo){
			map.put("status", "n");
			map.put("info", "根据UID未查询到用户信息");
			return gson.toJson(map);
		}
		UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(uid);
		if(null == userPlanner){
			map.put("status", "n");
			map.put("info", "根据UID未查询到规划师信息");
			return gson.toJson(map);
		}
		if(userPlanner.getStatus() == UserPlannerStatusEnum.AUDIT_PASS.getValue()){
			map.put("status", "n");
			map.put("info", "该规划师已经处于审核通过状态");
			return gson.toJson(map);
		}
		if(userInfo.getUserType() == UserInfoTypeEnum.NOMAL.getValue()){
			userInfo.setUserType(UserInfoTypeEnum.PLANNER.getValue());
			userInfo.setUpdateUid(-100);
			userInfo.setUpdateIp(this.getRemoteAddr());
			userInfoService.updateUserInfo(userInfo);
		}
		userPlanner.setStatus(UserPlannerStatusEnum.AUDIT_PASS.getValue());
		userPlanner.setUpdateUid(-100);
		userPlanner.setUpdateIp(this.getRemoteAddr());
		int result = userPlannerService.updateUserPlanner(userPlanner);
		if(result > 0){
			map.put("status", "y");
			map.put("info", "操作成功");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "操作失败，请稍后再试");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping(value="/nopass")
	public String plannerNopass(@RequestParam(value="uid", required=true) Integer uid){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == uid){
			map.put("status", "n");
			map.put("info", "未指定UID");
			return gson.toJson(map);
		}
		UserInfo userInfo = userInfoService.getUserInfoByUid(uid);
		if(null == userInfo){
			map.put("status", "n");
			map.put("info", "根据UID未查询到用户信息");
			return gson.toJson(map);
		}
		UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(uid);
		if(null == userPlanner){
			map.put("status", "n");
			map.put("info", "根据UID未查询到规划师信息");
			return gson.toJson(map);
		}
		userPlanner.setStatus(UserPlannerStatusEnum.AUDIT_REFUSE.getValue());
		userPlanner.setUpdateUid(-100);
		userPlanner.setUpdateIp(this.getRemoteAddr());
		int result = userPlannerService.updateUserPlanner(userPlanner);
		if(result > 0){
			map.put("status", "y");
			map.put("info", "操作成功");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "操作失败，请稍后再试");
			return gson.toJson(map);
		}
	}
}

