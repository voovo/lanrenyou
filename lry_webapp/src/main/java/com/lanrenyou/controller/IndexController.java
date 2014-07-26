package com.lanrenyou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;

@Controller
@RequestMapping("")
public class IndexController  extends BaseController {
	
	@RequestMapping(value={"/index", "/", ""})
	public ModelAndView toAddPage(){
		return new ModelAndView("/index");
	}

}
