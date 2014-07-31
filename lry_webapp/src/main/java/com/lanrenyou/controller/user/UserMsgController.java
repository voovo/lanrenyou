package com.lanrenyou.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.controller.travel.TravelShowUtil;
import com.lanrenyou.letter.enums.PrivateLetterHasReadEnum;
import com.lanrenyou.letter.enums.PrivateLetterHasReplyEnum;
import com.lanrenyou.letter.model.PrivateLetter;
import com.lanrenyou.letter.service.IPrivateLetterService;
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

@Controller
@RequestMapping("/user/{uid:[\\d]+}/msg")
public class UserMsgController  extends BaseController {
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IPrivateLetterService privateLetterService;
	
	
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
		ModelAndView mav = new ModelAndView("/user/user_msg");
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("userInfo", this.getCurrentUser());
		PageIterator<PrivateLetter> pageIter = privateLetterService.pageQueryPrivateLetter(this.getLoginUser().getId(), pageNo, pageSize);
		mav.addObject("pageIter", pageIter);
		Set<Integer> uidSet = new HashSet<Integer>();
		if(null != pageIter && null != pageIter.getData()){
			for(PrivateLetter letter : pageIter.getData()){
				uidSet.add(letter.getSenderUid());
			}
			mav.addObject("letterList", pageIter.getData());
		}
		
		List<Integer> uidList = new ArrayList<Integer>(uidSet);
		Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);
		mav.addObject("userInfoMap", userInfoMap);
		
		return mav;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String addLetter(
			@RequestParam(value = "toUid", required = true) Integer toUid,
			@RequestParam(value = "content", required = true) String content
		){
		PrivateLetter privateLetter = new PrivateLetter();
		privateLetter.setSenderUid(this.getLoginUser().getId());
		privateLetter.setReceiverUid(toUid);
		privateLetter.setHasRead(PrivateLetterHasReadEnum.UN_READ.getValue());
		privateLetter.setHasReply(PrivateLetterHasReplyEnum.UN_REPLY.getValue());
		privateLetter.setContent(content);
		
		int result = privateLetterService.addPrivateLetter(privateLetter);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(result > 0){
			map.put("status", "y");
			map.put("info", "发送成功");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "系统忙，请稍后重试");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping("/receiverDel")
	@ResponseBody
	public String deleteLetter(
			@RequestParam(value = "id", required = true) Integer id
		){
		Map<String, Object> map = new HashMap<String, Object>();
		PrivateLetter privateLetter = privateLetterService.findById(id);
		if(null == privateLetter){
			map.put("status", "n");
			map.put("info", "没有此ID对应的私信");
			return gson.toJson(map);
		}
		if(privateLetter.getReceiverUid().intValue() != this.getLoginUser().getId().intValue()){
			map.put("status", "n");
			map.put("info", "无权进行此操作");
			return gson.toJson(map);
		}
		int result = privateLetterService.receiverDelete(id);
		if(result > 0){
			map.put("status", "y");
			map.put("info", "删除成功");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "系统忙，请稍后重试");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping("/senderDel")
	@ResponseBody
	public String senderDeleteLetter(
			@RequestParam(value = "id", required = true) Integer id
		){
		Map<String, Object> map = new HashMap<String, Object>();
		PrivateLetter privateLetter = privateLetterService.findById(id);
		if(null == privateLetter){
			map.put("status", "n");
			map.put("info", "没有此ID对应的私信");
			return gson.toJson(map);
		}
		if(privateLetter.getSenderUid().intValue() != this.getLoginUser().getId().intValue()){
			map.put("status", "n");
			map.put("info", "无权进行此操作");
			return gson.toJson(map);
		}
		int result = privateLetterService.senderDelete(id);
		if(result > 0){
			map.put("status", "y");
			map.put("info", "删除成功");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "系统忙，请稍后重试");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping("/toReply")
	public ModelAndView toReply(
			@RequestParam(value = "receiverUid", required = true) Integer receiverUid
			){
		ModelAndView mav = new ModelAndView("/user/user_msg_reply");
		if(null == receiverUid){
			return toError("请选择回复对象");
		}
		UserInfo receiver = userInfoService.getUserInfoByUid(receiverUid);
		if(null == receiver){
			return toError("被回复人不存在");
		}
		mav.addObject("receiver", receiver);
		List<PrivateLetter> letterList = privateLetterService.getPrivateLetterOfTwoManForUidA(this.getLoginUser().getId(), receiverUid);
		mav.addObject("letterList", letterList);
		mav.addObject("userInfo", this.getCurrentUser());
		
		Map<Integer, UserInfo> userMap = new HashMap<Integer, UserInfo>();
		userMap.put(receiverUid, receiver);
		userMap.put(this.getLoginUser().getId(), this.getLoginUser());
		mav.addObject("userMap", userMap);
		
		return mav;
	}
	
	@RequestMapping(value= "/reply", method=RequestMethod.POST)
	public ModelAndView reply(
			@RequestParam(value = "receiverUid", required = true) Integer receiverUid,
			@RequestParam(value = "content", required = true) String content
			){
		if(null == receiverUid){
			return toError("请选择回复对象");
		}
		UserInfo receiver = userInfoService.getUserInfoByUid(receiverUid);
		if(null == receiver){
			return toError("被回复人不存在");
		}
		PrivateLetter letter = new PrivateLetter();
		letter.setSenderUid(this.getLoginUser().getId());
		letter.setReceiverUid(receiverUid);
		letter.setContent(content);
		letter.setHasRead(PrivateLetterHasReadEnum.UN_READ.getValue());
		letter.setHasReply(PrivateLetterHasReplyEnum.UN_REPLY.getValue());
		letter.setReceiverDeleted(0);
		letter.setSenderDeleted(0);
		int result = privateLetterService.addPrivateLetter(letter);
		if(result > 0) {
			ModelAndView mav = new ModelAndView(new RedirectView("/user/"+ this.getLoginUser().getId()+"/msg/list"));
			return mav;
		} else {
			return toError("系统忙，请稍后重试");
		}
	}
	
	@RequestMapping("/hasRead")
	@ResponseBody
	public String update2HasRead(@RequestParam(value = "ids", required = true) String ids){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(ids)){
			map.put("status", "n");
			map.put("info", "没有选择要更新的私信");
			return gson.toJson(map);
		}
		List<Integer> idList = new ArrayList<Integer>();
		for(String s : ids.split(",")){
			if(NumberUtils.isNumber(s)){
				idList.add(Integer.parseInt(s));
			}
		}
		int result = privateLetterService.updateHasRead(idList);
		if(result > 0){
			map.put("status", "y");
			map.put("info", "操作成功");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "系统忙，请稍后重试");
			return gson.toJson(map);
		}
	}
}
