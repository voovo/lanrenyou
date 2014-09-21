package com.lanrenyou.admin.controller.audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.support.PageIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;


@Controller
@RequestMapping("/audit/travel")
public class AuditTravelController extends BaseController {
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value="/list")
	public ModelAndView travelList(
			@RequestParam(value="queryTid",required=false, defaultValue="-1") Integer tid,
			@RequestParam(value="queryStatus",required=false, defaultValue="-1") Integer status,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="10") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/audit/travel_list");
		
		if(null == status ){
			status = -1;
		}
		if(null == tid){
			tid = -1;
		}
		PageIterator<TravelInfo> pageIter = travelInfoService.pageQueryByTidStatus(tid, status, pageNo, pageSize);
		mav.addObject("pageIter", pageIter);
		if(null != pageIter && null != pageIter.getData()){
			List<Integer> uidList = new ArrayList<Integer>();
			for(TravelInfo ti : pageIter.getData()){
				uidList.add(ti.getUid());
			}
			if(null != uidList && uidList.size() > 0){
				Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);
				mav.addObject("userInfoMap", userInfoMap);
			}
		}
		return mav;
	}

	@RequestMapping(value="/pass")
	public String travelPass(@RequestParam(value="tid", required=true) Integer tid){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == tid){
			map.put("status", "n");
			map.put("info", "未指定游记ID");
			return gson.toJson(map);
		}
		TravelInfo travelInfo = travelInfoService.getTravelInfoById(tid);
		if(null == travelInfo){
			map.put("status", "n");
			map.put("info", "根据游记ID未查询到游记信息");
			return gson.toJson(map);
		}
		travelInfo.setStatus(TravelInfoStatusEnum.PASS.getValue());
		travelInfo.setUpdateUid(-100);
		travelInfo.setUpdateIp(this.getRemoteAddr());
		int result = travelInfoService.updateTravelInfo(travelInfo);
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
	public String travelNopass(@RequestParam(value="tid", required=true) Integer tid){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == tid){
			map.put("status", "n");
			map.put("info", "未指定游记ID");
			return gson.toJson(map);
		}
		TravelInfo travelInfo = travelInfoService.getTravelInfoById(tid);
		if(null == travelInfo){
			map.put("status", "n");
			map.put("info", "根据游记ID未查询到游记信息");
			return gson.toJson(map);
		}
		travelInfo.setStatus(TravelInfoStatusEnum.UNPASS.getValue());
		travelInfo.setUpdateUid(-100);
		travelInfo.setUpdateIp(this.getRemoteAddr());
		int result = travelInfoService.updateTravelInfo(travelInfo);
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

