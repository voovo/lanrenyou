package com.lanrenyou.controller.base;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lanrenyou.config.AppConfigs;
import com.lanrenyou.util.ImageUtils;

@Controller
@RequestMapping("/upload")
public class FileUploadController{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected static final Gson gson = new Gson();
	
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);
	
	private static DateFormat fileNameFormat = new SimpleDateFormat("HHmmssSSS");
	private static DateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	@RequestMapping("/toPage")
	public ModelAndView toPage(HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/common/upload");
		return mav;
	}
	
	/*
	 * 一次上传多个图片，暂时没有，不公开
	 */
//	public String uploadFiles(@RequestParam(value="upload_files") MultipartFile[] uploadFiles, HttpServletRequest request) throws Exception {
//		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解 
//        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解 
//        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件 
//		Map<String, String> map = new HashMap<String, String>();
//		if(null == uploadFiles || uploadFiles.length <= 0){
//			map.put("status", "n");
//        	map.put("info", "文件未上传");
//			return gson.toJson(map);
//		}
//        for(MultipartFile file : uploadFiles){ 
//            if(!file.isEmpty()){
//                logger.info("文件名称: " + file.getName()); 
//                logger.info("文件原名: " + file.getOriginalFilename());
//                Date date = new Date();
//                String dateStr = format.format(date);
//                String year = dateStr.substring(0, 4);
//                String month = dateStr.substring(4,6);
//                String day = dateStr.substring(6);
//                String fileName = fileNameFormat.format(date) +"."+ file.getName().substring(file.getName().lastIndexOf('.'));
//                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中 
//                String realPath = (AppConfigs.getInstance().get("upload.path")+year+"/"+month+"/"+day); 
//                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的 
//                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, fileName)); 
//                map.put("status", "y");
//            	map.put("url", "http://img.lanrenyou.com/"+year+"/"+month+"/"+day+"/"+fileName);
//            } 
//        }
//        return "1";
//	}
	
	@RequestMapping(value="/submit", method=RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(value="upload_file") MultipartFile uploadFile, HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
        if(uploadFile.isEmpty()){
        	map.put("status", "n");
        	map.put("info", "文件未上传");
			return gson.toJson(map);
        }else{
        	Date date = new Date();
            String dateStr = format.format(date);
            String year = dateStr.substring(0, 4);
            String month = dateStr.substring(4,6);
            String day = dateStr.substring(6);
            final String fileName = fileNameFormat.format(date) + uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf('.'));
            final String realPath = (AppConfigs.getInstance().get("upload.path")+year+"/"+month+"/"+day); 
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), new File(realPath, fileName));
            EXECUTOR_SERVICE.submit(new Runnable() {
                @Override
                public void run() {
                	try {
						ImageUtils.cropImageForTravel(realPath + "/" + fileName);
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            });
            map.put("status", "y");
        	map.put("url", "http://img.lanrenyou.com/"+year+"/"+month+"/"+day+"/"+fileName);
        	return gson.toJson(map);
        } 
	}
}
