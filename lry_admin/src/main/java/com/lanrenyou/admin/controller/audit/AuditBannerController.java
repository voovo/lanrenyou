package com.lanrenyou.admin.controller.audit;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.admin.controller.base.BaseController;
import com.lanrenyou.travel.model.IndexBanner;
import com.lanrenyou.travel.service.IIndexBannerService;
import com.lanrenyou.util.ConfigProperties;


@Controller
@RequestMapping("/audit/index_banner")
public class AuditBannerController extends BaseController {
	
	private static DateFormat fileNameFormat = new SimpleDateFormat("HHmmssSSS");
	private static DateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	@Autowired
	private IIndexBannerService indexBannerService;
	
	@RequestMapping(value="/list")
	public ModelAndView bannerList(
			@RequestParam(value="isDel",required=false, defaultValue="0") Integer isDel,
			@RequestParam(value="pageNo",required=false, defaultValue="1") int pageNo,
			@RequestParam(value="pageSize",required=false, defaultValue="10") int pageSize
		){
		ModelAndView mav = new ModelAndView();
		mav.addObject("isDel", isDel);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.setViewName("/admin/audit/index_banner_list");
		PageIterator<IndexBanner> pageIter = indexBannerService.pageQueryByStatus(isDel, pageNo, pageSize);
		if(null != pageIter && null != pageIter.getData() && pageIter.getData().size() > 0){
			mav.addObject("pageIter", pageIter);
		}
		
		return mav;
	}
	
	@RequestMapping(value="/addpage")
	public ModelAndView addPage(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/audit/index_banner_add");
		return mav;
	}

	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(value="upfile") MultipartFile upfile,
			@RequestParam(value="alt", required=false, defaultValue="") String alt,
			@RequestParam(value="linkUrl", required=true) String linkUrl) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if(upfile.isEmpty()){ 
            map.put("status", "n");
            map.put("info", "文件未上传"); 
            return gson.toJson(map);
        }else{
        	String filename = upfile.getOriginalFilename();
            Date date = new Date();
            String dateStr = format.format(date);
            String year = dateStr.substring(0, 4);
            String month = dateStr.substring(4,6);
            String day = dateStr.substring(6);
            String fileSuffix = filename.substring(filename.lastIndexOf('.')).toLowerCase();
            final String fileName = fileNameFormat.format(date) + fileSuffix;
            final String realPath = (ConfigProperties.getProperty("upload.path")+year+"/"+month+"/"+day); 
            FileUtils.copyInputStreamToFile(upfile.getInputStream(), new File(realPath, fileName)); 
            String picUrl = "http://img.lanrenyou.com/"+year+"/"+month+"/"+day+"/"+fileName;
            IndexBanner ib = new IndexBanner();
            ib.setAlt(alt);
            ib.setPicUrl(picUrl);
            ib.setLinkUrl(linkUrl);
            ib.setIsDel(0);
            ib.setSort(0);
            
            int result = indexBannerService.addIndexBanner(ib);
            
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
		int result = indexBannerService.updateToDel(id);
		
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


