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
import com.lanrenyou.letter.service.IPrivateLetterService;
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;


@Controller
@RequestMapping("/msg")
public class AdminMsgController extends BaseController {
	
	@Autowired
	private IPrivateLetterService privateLetterService;
	
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
		
		return mav;
	}

}

