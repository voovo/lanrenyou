
package com.lanrenyou.controller.contactUs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;


@Controller
@RequestMapping("/contactus")
public class ContactUsController extends BaseController {
	@RequestMapping({"","/"})
	public ModelAndView contactUs(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("contactUs/contact-us");
		return mav;
	}
	
	
	@RequestMapping("/globalOffices")
	public ModelAndView globalOffices(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("contactUs/global-offices/global-offices");
		return mav;
	}
}

