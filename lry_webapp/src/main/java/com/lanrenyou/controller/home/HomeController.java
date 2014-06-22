
package com.lanrenyou.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;


@Controller
@RequestMapping({"","/"})
public class HomeController extends BaseController {
	@RequestMapping()
	public ModelAndView home(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/test")
	public ModelAndView test(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test");
		return mav;
	}
}

