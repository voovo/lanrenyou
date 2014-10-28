package com.lanrenyou.admin.controller.audit;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import mybatis.framework.core.support.PageIterator;

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

import com.google.gson.reflect.TypeToken;
import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.travel.enums.TravelInfoStatusEnum;
import com.lanrenyou.travel.model.IndexBanner;
import com.lanrenyou.travel.model.IndexTravel;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.IIndexBannerService;
import com.lanrenyou.travel.service.IIndexTravelService;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.util.ConfigProperties;
import com.lanrenyou.util.StringUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


@Controller
@RequestMapping("/audit/index_banner")
public class AuditBannerController extends BaseController {
	
	@Autowired
	private IIndexBannerService indexBannerService;
	
	@RequestMapping(value="/list")
	public ModelAndView plannerList(
			@RequestParam(value="isDel",required=false, defaultValue="0") Integer isDel,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="10") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		mav.addObject("isDel", isDel);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
			mav.setViewName("/admin/audit/index_data_list");
			PageIterator<IndexBanner> pageIter = indexBannerService.pageQueryByStatus(isDel, pageNo, pageSize);
			if(null != pageIter && null != pageIter.getData() && pageIter.getData().size() > 0){
				mav.addObject("pageIter", pageIter);
			}
		
		return mav;
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
	@ResponseBody
	public String add(@RequestParam(value="tid", required=true) Integer tid, @RequestParam(value="srcType", required=true) Character srcType){
		Map<String, Object> map = new HashMap<String, Object>();
		
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
	
	@RequestMapping(value="/remove", method=RequestMethod.GET)
	@ResponseBody
	public String remove(@RequestParam(value="id", required=true) Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		IndexBanner ib = indexBannerService.findById(id);
		if(null == ib){
			map.put("status", "n");
			map.put("info", "数据错误");
			return gson.toJson(map);
		}
		int result = indexBannerService.(tid);
		
		if(result > 0){
			map.put("status", "y");
			map.put("info", "操作成功");
			return gson.toJson(map);
		} else {
			map.put("status", "n");
			map.put("info", "操作失败，请稍后再试");
			return gson.toJson(map);
		}
	}
	
}


