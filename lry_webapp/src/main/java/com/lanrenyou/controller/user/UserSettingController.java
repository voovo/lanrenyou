package com.lanrenyou.controller.user;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.lanrenyou.common.PasswordUtil;
import com.lanrenyou.config.AppConfigs;
import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.controller.travel.TravelShowUtil;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import com.lanrenyou.travel.service.ITravelCollectService;
import com.lanrenyou.travel.model.TravelCollect;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.user.enums.UserInfoStatusEnum;
import com.lanrenyou.user.enums.UserPlannerStatusEnum;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserFollowService;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;
import com.lanrenyou.util.ServletUtil;
import com.lanrenyou.util.constants.LRYConstant;

@Controller
@RequestMapping("/user/setting")
public class UserSettingController  extends BaseController {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	private static DateFormat fileNameFormat = new SimpleDateFormat("HHmmssSSS");
	private static DateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	@RequestMapping("/info")
	public ModelAndView toInfoPage() {
		if(null == this.getLoginUser()){
			return to404();
		}
		ModelAndView mav = new ModelAndView("/user/user_setting_info");
		mav.addObject("userInfo", this.getLoginUser());
		
		UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(this.getLoginUser().getId());
		mav.addObject("userPlanner", userPlanner);
		
		return mav;
	}
	
	@RequestMapping("/infoSubmit")
	public ModelAndView infoSubmit(
			HttpServletResponse response,
            @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
            @RequestParam(value = "userIntro", required = false, defaultValue = "") String userIntro,
            @RequestParam(value = "presentAddress", required = false, defaultValue = "") String presentAddress,
            @RequestParam(value = "previousAddress", required = false, defaultValue = "") String previousAddress,
            @RequestParam(value = "wechat", required = false, defaultValue = "") String wechat,
            @RequestParam(value = "weiboName", required = false, defaultValue = "") String weiboName,
            @RequestParam(value = "weiboUrl", required = false, defaultValue = "") String weiboUrl,
            @RequestParam(value = "targetCity", required = false, defaultValue = "") String targetCity,
            @RequestParam(value = "fees", required = false, defaultValue = "") String fees){
		UserInfo userInfo = userInfoService.getUserInfoByUid(this.getLoginUser().getId());
		if(null == userInfo){
			return toError("用户信息不存在");
		}
		boolean hasUpdateUserInfo = false;
		if(StringUtils.isNotBlank(nickname.trim())){
			userInfo.setNickname(nickname.trim());
			hasUpdateUserInfo = true;
		}
		if(StringUtils.isNotBlank(userIntro)){
			userInfo.setUserIntro(userIntro);
			hasUpdateUserInfo = true;
		}
		if(StringUtils.isNotBlank(presentAddress)){
			userInfo.setPresentAddress(presentAddress);
			hasUpdateUserInfo = true;
		}
		if(StringUtils.isNotBlank(previousAddress)){
			userInfo.setPreviousAddress(previousAddress);
			hasUpdateUserInfo = true;
		}
		if(StringUtils.isNotBlank(wechat)){
			userInfo.setWechatName(wechat);
			hasUpdateUserInfo = true;
		}
		if(StringUtils.isNotBlank(weiboName)){
			userInfo.setWeiboName(weiboName);
			hasUpdateUserInfo = true;
		}
		if(StringUtils.isNotBlank(weiboUrl)){
			userInfo.setWeiboUrl(weiboUrl);
			hasUpdateUserInfo = true;
		}
		
		if(hasUpdateUserInfo){
			userInfo.setUpdateUid(this.getLoginUser().getId());
			userInfo.setUpdateIp(this.getRemoteAddr());
			userInfoService.updateUserInfo(userInfo);
		}
		
		if(StringUtils.isNotBlank(targetCity)){
			UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(this.getLoginUser().getId());
			if(null == userPlanner){
				userPlanner = new UserPlanner();
				userPlanner.setUid(this.getLoginUser().getId());
				userPlanner.setStatus(UserPlannerStatusEnum.WAIT_AUDIT.getValue());
				userPlanner.setCreateUid(this.getLoginUser().getId());
				userPlanner.setCreateIp(this.getRemoteAddr());
				userPlanner.setCreateTime(new Date());
			}
			userPlanner.setTargetCity(targetCity);
			userPlanner.setFees(fees);
			userPlanner.setUpdateUid(this.getLoginUser().getId());
			userPlanner.setUpdateIp(this.getRemoteAddr());
			userPlannerService.updateUserPlanner(userPlanner);
		}
		ModelAndView mav = new ModelAndView(new RedirectView("/user/"+this.getLoginUser().getId()));
		return mav;
	}
	
	@RequestMapping("/passwd")
	public ModelAndView toChangePasswdPage() {
		if(null == this.getLoginUser()){
			return to404();
		}
		ModelAndView mav = new ModelAndView("/user/user_setting_passwd");
		mav.addObject("userInfo", this.getLoginUser());
		return mav;
	}
	
	@RequestMapping("/changePasswd")
	@ResponseBody
	public String changePasswd(
			HttpServletResponse response,
			@RequestParam(value = "old_passwd", required = false, defaultValue = "") String oldPasswd,
            @RequestParam(value = "new_passwd", required = false, defaultValue = "") String newPasswd,
            @RequestParam(value = "new_repasswd", required = false, defaultValue = "") String newRepasswd
			) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null == this.getLoginUser()){
			map.put("status", "n");
			map.put("info", "请先登录");
			return gson.toJson(map);
		}
		
		if(!newPasswd.equals(newRepasswd)){
			map.put("status", "n");
			map.put("info", "两次输入密码不一致");
			return gson.toJson(map);
		}
		
		if(!PasswordUtil.convertToMd5(oldPasswd).equals(this.getLoginUser().getUserPass())){
			map.put("status", "n");
			map.put("info", "用户密码错误");
			return gson.toJson(map);
		}
		
		this.getLoginUser().setUserPass(PasswordUtil.convertToMd5(newPasswd));
		this.getLoginUser().setHistoryPasswd(oldPasswd);
		this.getLoginUser().setUpdateUid(this.getLoginUser().getId());
		this.getLoginUser().setUpdateIp(this.getRemoteAddr());
		int result = userInfoService.updateUserInfo(this.getLoginUser());
		
		if(result > 0){
			ServletUtil.deleteCookie(request, response, LRYConstant.AUTH_COOKIE_KEY);
			map.put("status", "y");
			map.put("url", "/login");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "系统忙，请稍后重试");
			return gson.toJson(map);
		}
	}
	
	@RequestMapping("/avatar")
	public ModelAndView toSetAVatarPage() {
		if(null == this.getLoginUser()){
			return to404();
		}
		ModelAndView mav = new ModelAndView("/user/user_setting_avatar");
		mav.addObject("userInfo", this.getLoginUser());
		return mav;
	}
	
	@RequestMapping(value="/avatarSubmit", method=RequestMethod.POST)
	public ModelAndView avatarSubmit(@RequestParam(value="avatar", required=true, defaultValue="") String avatarUrl ) throws Exception {
        if(StringUtils.isBlank(avatarUrl)){
        	return toError("请先上传头像");
        }else {
        	if(avatarUrl.contains("img.lanrenyou.com")&& !avatarUrl.endsWith("_s.jpg")){
        		avatarUrl = avatarUrl.replace(".jpg", "_s.jpg");
        	}
            this.getLoginUser().setAvatar(avatarUrl);
            this.getLoginUser().setUpdateUid(this.getLoginUser().getId());
            this.getLoginUser().setUpdateIp(this.getRemoteAddr());
            int result = userInfoService.doUpdateById(getLoginUser());
            if(result > 0){
    			ModelAndView mav = new ModelAndView(new RedirectView("/user/"+this.getLoginUser().getId()));
    			return mav;
    		} else {
    			return toError("系统忙，请稍后重试");
    		}
        } 
	}
	
	@RequestMapping("/applyToPlanner")
	public ModelAndView applyToPlanner(
			HttpServletResponse response,
            @RequestParam(value = "fees", required = false) String fees,
            @RequestParam(value = "targetCity", required = false, defaultValue = "") String targetCity){
		UserInfo userInfo = userInfoService.getUserInfoByUid(this.getLoginUser().getId());
		if(null == userInfo){
			return toError("用户信息不存在");
		}
		
		boolean isSave = false;
		UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(this.getLoginUser().getId());
		if(null == userPlanner){
			isSave = true;
			userPlanner = new UserPlanner();
			userPlanner.setUid(this.getLoginUser().getId());
			userPlanner.setCreateUid(this.getLoginUser().getId());
			userPlanner.setCreateIp(this.getRemoteAddr());
			userPlanner.setCreateTime(new Date());
		} 
		userPlanner.setTargetCity(targetCity);
		userPlanner.setFees(fees);
		userPlanner.setStatus(UserPlannerStatusEnum.WAIT_AUDIT.getValue());
		userPlanner.setUpdateUid(this.getLoginUser().getId());
		userPlanner.setUpdateIp(this.getRemoteAddr());
		if(isSave){
			userPlannerService.addUserPlanner(userPlanner);
		} else {
			userPlannerService.updateUserPlanner(userPlanner);
		}
		ModelAndView mav = new ModelAndView("/user/user_applyplanner_prompt"); 
		mav.addObject("promptMessage", "用户申请已提交，系统管理员将24小时内完成审核，请耐心等待");
		return mav;
	}
	
//	@RequestMapping(value="/avatarSubmit", method=RequestMethod.POST)
//	@ResponseBody
//	public String upload(@RequestParam(value="myfiles") MultipartFile myfilest) throws Exception {
//		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解 
//        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解 
//        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件 
//        for(MultipartFile myfile : myfiles){ 
//            if(myfile.isEmpty()){ 
//                System.out.println("文件未上传"); 
//            }else{ 
//                System.out.println("文件长度: " + myfile.getSize()); 
//                System.out.println("文件类型: " + myfile.getContentType()); 
//                System.out.println("文件名称: " + myfile.getName()); 
//                System.out.println("文件原名: " + myfile.getOriginalFilename()); 
//                System.out.println("========================================"); 
//                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中 
//                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload"); 
//                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的 
//                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename())); 
//            } 
//        }
//        return "1";
//	}
}
