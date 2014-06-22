
package com.lanrenyou.controller.about;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;


@Controller
@RequestMapping("/about")
public class AboutController extends BaseController {
	
	@RequestMapping()
	public ModelAndView about(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("about/about");
		return mav;
	}
	
	@RequestMapping("/aboutUs")
	public ModelAndView aboutUs(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("about/sub-title-four/sub-title-four");
		return mav;
	}
	
	@RequestMapping("/ourAdvantages")
	public ModelAndView ourAdvantages(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("about/sub-title-one/sub-title-one");
		return mav;
	}
	
	@RequestMapping("/fundsSafty")
	public ModelAndView fundsSafty(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("about/funds-safety");
		return mav;
	}
}

