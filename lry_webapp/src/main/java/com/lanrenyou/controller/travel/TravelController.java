package com.lanrenyou.controller.travel;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.config.AppConfigs;
import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.enums.TravelInfoIsEliteEnum;
import com.lanrenyou.travel.enums.TravelInfoIsTopEnum;
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.user.enums.UserPlannerStatusEnum;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserPlannerService;

@Controller
@RequestMapping("/travel")
public class TravelController  extends BaseController {
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);
	
	@RequestMapping("/toAddPage")
	public ModelAndView toAddPage(){
		UserInfo userInfo = getLoginUser();
		if(null == userInfo){
			return toError("请先登录");
		}
		UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(userInfo.getId());
		if(null == userPlanner){
			return new ModelAndView("/user/user_apply_toplanner");
		}
		if(userPlanner.getStatus() == UserPlannerStatusEnum.WAIT_AUDIT.getValue()){
			ModelAndView mav = new ModelAndView("/user/user_applyplanner_prompt"); 
			mav.addObject("promptMessage", "用户申请处于待审核状态，系统管理员将24小时内完成审核，请耐心等待");
			return mav;
		}
		if(userPlanner.getStatus() == UserPlannerStatusEnum.AUDIT_REFUSE.getValue()){
			ModelAndView mav = new ModelAndView("/user/user_applyplanner_prompt"); 
			String promptMessage = "";
			if(StringUtils.isNotBlank(userPlanner.getRefuseReason())){
				promptMessage = "用户申请被拒绝，拒绝原因：" + userPlanner.getRefuseReason();
			} else {
				promptMessage = "用户申请被拒绝，请校验修改信息后重新申请" ;
			}
			mav.addObject("promptMessage", promptMessage);
			return mav;
		}
		if(userPlanner.getStatus() == UserPlannerStatusEnum.DELETE.getValue()){
			return new ModelAndView("/user/user_apply_toplanner");
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
		UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(userInfo.getId());
		if(null == userPlanner){
			map.put("status", "n");
			map.put("info", "只有规划师才能发表游记");
			return gson.toJson(map);
		}
		if(userPlanner.getStatus() == UserPlannerStatusEnum.WAIT_AUDIT.getValue()){
			map.put("status", "n");
			map.put("info", "该用户处于待审核状态");
			return gson.toJson(map);
		}
		if(userPlanner.getStatus() == UserPlannerStatusEnum.DELETE.getValue()){
			map.put("status", "n");
			map.put("info", "该规划师已被删除，请重新请求认证");
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
		final int tid = travelInfo.getId();
		EXECUTOR_SERVICE.submit(new Runnable() {
    		@Override
    		public void run() {
    			updateTravel(tid);
    		}
    	});
		return gson.toJson(map);
	}
	
	public void updateTravel(int tid) {  
		HttpClient client = new HttpClient();
	    HttpMethod method = new GetMethod("http://"+AppConfigs.getInstance().get("domains.www")+"/search_index/travel/update?password=woailanrenyou&tids="+tid);    // 使用 POST 方式提交数据 
	    try {
			client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			method.releaseConnection();  
		}
    }  
}
