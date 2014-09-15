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
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;


@Controller
@RequestMapping("/msg")
public class AdminMsgController extends BaseController {
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
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
			List<Integer> tidList = new ArrayList<Integer>();
			for(TravelInfo ti : pageIter.getData()){
				tidList.add(ti.getId());
			}
			if(tidList.size() > 0){
				List<TravelContent> travelContentList = travelContentService.getTravelContentListByTidList(tidList);
			}
		}
		return mav;
	}

}

