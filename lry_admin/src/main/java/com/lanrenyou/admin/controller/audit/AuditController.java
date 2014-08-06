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
import com.lanrenyou.util.MailUtil;
import com.lanrenyou.util.freemarker.FreemarkerUtil;


@Controller
@RequestMapping("/admin/audit")
public class AuditController extends BaseController {
	
	
	@RequestMapping(value="/user_list")
	public ModelAndView list(
			@RequestParam(value="querycountry",required=false, defaultValue="-1") int querycountry,
			@RequestParam(value="querystatus",required=false, defaultValue="-1") int status,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="10") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/audit/to_audit_user_list");
		
		return mav;
	}

}

