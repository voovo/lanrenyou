package com.lanrenyou.controller.travel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import com.lanrenyou.travel.model.TravelVisitLog;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.util.ServletUtil;

@Controller
@RequestMapping("/travel")
public class TravelController  extends BaseController {
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@RequestMapping("/toAddPage")
	public ModelAndView toAddPage(){
		UserInfo userInfo = getLoginUser();
		if(null == userInfo){
			return toError("请先登录");
		}
		return new ModelAndView("/travel/travel_add");
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
	@ResponseBody
	public String create(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "n");
		map.put("info", "系统正忙，请稍后再试");
		return gson.toJson(map);
	}
}
