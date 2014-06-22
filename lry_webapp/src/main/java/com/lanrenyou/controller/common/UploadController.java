
package com.lanrenyou.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;


@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {
	
	@RequestMapping("/toUploadPage")
	public ModelAndView toUploadPage(@RequestParam("type") Integer type){
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("profileHeaderSelected", "contactInfo");
		mav.setViewName("payment/profile-contact-info");
		return mav;
	}
	
}

