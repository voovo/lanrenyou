package com.lanrenyou.admin.controller.msg;

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
import com.lanrenyou.letter.model.PrivateLetter;
import com.lanrenyou.letter.service.IPrivateLetterService;
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;


@Controller
@RequestMapping("/msg")
public class AdminMsgController extends BaseController {
	
	@Autowired
	private IPrivateLetterService privateLetterService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value="/list")
	public ModelAndView travelList(
			@RequestParam(value="queryUid",required=false, defaultValue="-1") Integer queryUid,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="10") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/msg/letter_list");
		
		if(null == queryUid){
			queryUid = -1;
		}
		PageIterator<Map<String, Object>> pageIter = privateLetterService.pageQueryByUid(queryUid, pageNo, pageSize);
		mav.addObject("pageIter", pageIter);
		
		return mav;
	}
	
	@RequestMapping(value="/showMsg")
	public ModelAndView showMsgBetweenUidAAndUidB(
			@RequestParam(value="senderUid",required=true) Integer senderUid,
			@RequestParam(value="receiverUid",required=true) Integer receiverUid){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/msg/show_msg");

		UserInfo receiver = userInfoService.getUserInfoByUid(receiverUid);
		if(null == receiver){
			return toError("被回复人不存在");
		}
		mav.addObject("receiver", receiver);
		UserInfo sender = userInfoService.getUserInfoByUid(senderUid);
		if(null == sender){
			return toError("主回复人不存在");
		}
		mav.addObject("sender", sender);
		
		List<PrivateLetter> letterList = privateLetterService.getPrivateLetterOfTwoManForUidA(senderUid, receiverUid);
		mav.addObject("letterList", letterList);
		
		Map<Integer, UserInfo> userMap = new HashMap<Integer, UserInfo>();
		userMap.put(receiverUid, receiver);
		userMap.put(senderUid, sender);
		mav.addObject("userMap", userMap);
		
		return mav;
	}

}

