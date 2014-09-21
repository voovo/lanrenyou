package com.lanrenyou.admin.controller.msg;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.letter.model.PrivateLetter;
import com.lanrenyou.letter.service.IPrivateLetterService;
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
		mav.addObject("queryUid", queryUid);
		mav.addObject("pageNo", pageNo);
		
		if(null == queryUid){
			queryUid = -1;
		}
		PageIterator<Map<String, Object>> pageIter = privateLetterService.pageQueryByUid(queryUid, pageNo, pageSize);
		mav.addObject("pageIter", pageIter);
		if(null != pageIter && pageIter.getData() != null){
			Set<Integer> uidSet = new HashSet<Integer>();
			for(Map<String, Object> map : pageIter.getData()){
				Integer senderUid = (Integer) map.get("sender_uid");
				if(null != senderUid){
					uidSet.add(senderUid);
				}
				Integer receiverUid = (Integer) map.get("receiver_uid");
				if(null != receiverUid){
					uidSet.add(receiverUid);
				}
			}
			
			Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(new ArrayList<Integer>(uidSet));
			mav.addObject("userInfoMap", userInfoMap);
		}
		
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

