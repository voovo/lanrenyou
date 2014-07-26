package com.lanrenyou.controller.travel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.enums.TravelInfoIsEliteEnum;
import com.lanrenyou.travel.enums.TravelInfoIsTopEnum;
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.user.model.UserInfo;

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

	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public String create(
			@RequestParam(value = "title", required=true, defaultValue="") String title,
			@RequestParam(value = "area", required=true, defaultValue="") String area,
			@RequestParam(value = "imgs", required=true, defaultValue="") String imgs
			){
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = getLoginUser();
		if(null == userInfo){
			map.put("status", "n");
			map.put("info", "请先登录");
			return gson.toJson(map);
		}
		TravelInfo travelInfo = new TravelInfo();
		travelInfo.setTitle(title.trim());
		travelInfo.setCity(area.trim());
		travelInfo.setIsElite(TravelInfoIsEliteEnum.NO.getValue());
		travelInfo.setIsTop(TravelInfoIsTopEnum.NO.getValue());
		travelInfo.setStatus(TravelInfoStatusEnum.NORMAL.getValue());
		travelInfo.setUid(userInfo.getId());
		travelInfo.setCreateUid(userInfo.getId());
		travelInfo.setCreateTime(new Date());
		travelInfo.setCreateIp(this.getRemoteAddr());
		int result = travelInfoService.addTravelInfo(travelInfo);
		if(result < 1){
			map.put("status", "n");
			map.put("info", "插入数据失败");
			return gson.toJson(map);
		}
		
		TravelContent travelContent = new TravelContent();
		travelContent.setTid(travelInfo.getId());
		travelContent.setContent(imgs);
		result = travelContentService.addTravelContent(travelContent);
		if(result < 1){
			map.put("status", "n");
			map.put("info", "插入游记内容失败，请稍后重试");
			return gson.toJson(map);
		}
		
		map.put("status", "y");
		map.put("info", "/travel/" + travelInfo.getId());
		return gson.toJson(map);
	}
}