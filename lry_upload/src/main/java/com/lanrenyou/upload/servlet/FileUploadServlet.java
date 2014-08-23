package com.lanrenyou.upload.servlet;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.lanrenyou.upload.util.FileUploadConfigProperties;
import com.lanrenyou.upload.util.ImageUtils;

public class FileUploadServlet extends HttpServlet {
	
	protected static final Gson gson = new Gson();
	
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);
	
	private static DateFormat fileNameFormat = new SimpleDateFormat("HHmmssSSS");
	private static DateFormat format = new SimpleDateFormat("yyyyMMdd");
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
	    	 map.put("status", "n");
	    	 map.put("info", "表单数据格式不是multipart/form-data，或者非法提交");
	    	 response.getWriter().print(gson.toJson(map));
	    	 return;
	    }
		DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024000);
        upload.setHeaderEncoding("utf-8");
        List<FileItem> fileList = null;
        try {
            fileList = upload.parseRequest(request);
        } catch (FileUploadException ex) {
        	map.put("status", "n");
	    	map.put("info", "解析上传数据出错");
	    	response.getWriter().print(gson.toJson(map));
	    	return;
        }

        Iterator<FileItem> it = fileList.iterator();
        List<String> urlList = new ArrayList<String>();
        while (it.hasNext()) {
            FileItem item = it.next();
            if (!item.isFormField() && item.getSize() > 0) {
            	String filename = item.getName();
                Date date = new Date();
                String dateStr = format.format(date);
                String year = dateStr.substring(0, 4);
                String month = dateStr.substring(4,6);
                String day = dateStr.substring(6);
                String fileSuffix = filename.substring(filename.lastIndexOf('.')).toLowerCase();
                boolean picDeal = true;
                if(fileSuffix.trim().equals(".jpg") || fileSuffix.trim().equals(".jpeg")){
                	fileSuffix = ".jpg";
                } else {
                	map.put("status", "n");
        	    	map.put("info", "请上传JPG格式的图片");
        	    	response.getWriter().print(gson.toJson(map));
        	    	return;
//                	picDeal = false;
                }
                final String fileName = fileNameFormat.format(date) + fileSuffix;
                final String realPath = (FileUploadConfigProperties.getProperty("upload.path")+year+"/"+month+"/"+day); 
                File file = new File(realPath);
                if(!file.exists()){
                	file.mkdirs();
                }
                
                file = new File(realPath, fileName);
                try {
                    item.write(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                urlList.add("http://img.lanrenyou.com/"+year+"/"+month+"/"+day+"/"+fileName);
                if(picDeal){
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
                } else {
                	EXECUTOR_SERVICE.submit(new Runnable() {
                		@Override
                		public void run() {
                			String srcPath = realPath + "/" + fileName;
                			String dJPGPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_d.jpg";
                			String sJPGPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_s.jpg";
                			String lJPGPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_l.jpg";
                			try {
								ImageUtils.copyFile(new File(srcPath), new File(dJPGPath));
								ImageUtils.copyFile(new File(srcPath), new File(sJPGPath));
								ImageUtils.copyFile(new File(srcPath), new File(lJPGPath));
							} catch (IOException e) {
								e.printStackTrace();
							}
                		}
                	});
                }
            }
        }
        map.put("status", "y");
        if(urlList.size() > 1){
        	map.put("url", urlList);
        } else if (urlList.size() == 1){
        	map.put("url", urlList.get(0));
        }
    	response.getWriter().print(gson.toJson(map));
	}
}