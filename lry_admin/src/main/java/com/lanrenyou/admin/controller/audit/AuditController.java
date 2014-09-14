package com.lanrenyou.admin.controller.audit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;
import com.lanrenyou.util.MailUtil;
import com.lanrenyou.util.freemarker.FreemarkerUtil;


@Controller
@RequestMapping("/audit")
public class AuditController extends BaseController {
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value="/planner")
	public ModelAndView plannerList(
			@RequestParam(value="queryName",required=false, defaultValue="-1") String queryName,
			@RequestParam(value="queryStatus",required=false, defaultValue="-1") Integer status,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="10") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/audit/planner_list");
		
		if(null == status ){
			status = -1;
		}
		if(status == -1 ){
		}
		
		return mav;
	}

}

