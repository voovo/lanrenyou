package com.lanrenyou.controller.travel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.letter.migrate.Migrate;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.user.dao.IUserInfoDao;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.util.ImageUtils;

@Controller
@RequestMapping("/olddata")
public class OldDataController extends BaseController {

	@Autowired
	private ITravelInfoService travelInfoService;

	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping("/dealPic")
	public ModelAndView dealPic() throws IOException, InterruptedException {
		String[] dirPath = {
				"/ROOT/www/www_8000/wp-content/uploads/2013/06",
				"/ROOT/www/www_8000/wp-content/uploads/2013/07",
				"/ROOT/www/www_8000/wp-content/uploads/2013/08",
				"/ROOT/www/www_8000/wp-content/uploads/2013/09",
				"/ROOT/www/www_8000/wp-content/uploads/2013/10",
				"/ROOT/www/www_8000/wp-content/uploads/2013/11",
				"/ROOT/www/www_8000/wp-content/uploads/2013/12",
				"/ROOT/www/www_8000/wp-content/uploads/2014/01",
				"/ROOT/www/www_8000/wp-content/uploads/2014/02",
				"/ROOT/www/www_8000/wp-content/uploads/2014/03",
				"/ROOT/www/www_8000/wp-content/uploads/2014/04",
				"/ROOT/www/www_8000/wp-content/uploads/2014/05",
				"/ROOT/www/www_8000/wp-content/uploads/2014/06",
				"/ROOT/www/www_8000/wp-content/uploads/2014/07",
				"/ROOT/www/www_8000/wp-content/uploads/2014/08"};
		for(String dir : dirPath){
			File file = new File(dir);
			String[] fileNames = file.list();
			if(null != fileNames){
				for(String fileName : fileNames){
					if(StringUtils.isNotBlank(fileName)){
						if((fileName.toLowerCase().endsWith(".jpg"))){
							ImageUtils.cropImageForTravel(dir+"/"+fileName);
						} else if (fileName.toLowerCase().endsWith(".jpeg")){
							String srcPath = dir + "/" + fileName;
							String sourceJPGPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + ".jpg";
							ImageUtils.copyFile(new File(srcPath), new File(sourceJPGPath));
							ImageUtils.cropImageForTravel(dir+"/"+fileName);
						} else {
							String srcPath = dir + "/" + fileName;
							String sourceJPGPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + ".jpg";
                			String sJPGPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_s.jpg";
                			String lJPGPath = srcPath.substring(0, srcPath.lastIndexOf('.')) + "_l.jpg";
               				ImageUtils.copyFile(new File(srcPath), new File(sourceJPGPath));
							ImageUtils.copyFile(new File(srcPath), new File(sJPGPath));
							ImageUtils.copyFile(new File(srcPath), new File(lJPGPath));
						}
						Thread.sleep(500);
					}
				}
			}
			logger.info("Deal Old Data Have Finish Dir:{}", dir);
		}

		return toError("执行完毕");
	}
	
	@RequestMapping("/dealOldTravel")
	public ModelAndView dealOldTravel() throws IOException {
		Migrate m = new Migrate();
		m.importUsers();
		m.importTravel(m.exportPost());
		return toError("执行完毕");
	}
	
//	@RequestMapping("/dealPlanner")
//	public ModelAndView dealPlanner() throws IOException {
//		List<UserInfo> list = userInfoService.findAll();
//		for(UserInfo userInfo : list){
//			if(null != userInfo && StringUtils.isNotBlank(userInfo.getAvatar())){
//				String img = userInfo.getAvatar();
//				img = img.replace("._s.", "_s.");
//				userInfo.setAvatar(img);
//				userInfoService.updateUserInfo(userInfo);
//			}
//		}
//		return toError("Yeah");
//	}
	public ModelAndView dealTravelInfo() throws IOException {
		List<TravelContent> list = travelContentService.getTravelContentListForSearchIndex(new Date(), 0, 800);
		for(TravelContent travelContent : list){
			
			String content = travelContent.getContent();
			if(StringUtils.isBlank(content)){
				continue;
			}
			
			List<Map<String, String>> jsonMapList = gson.fromJson(content, new TypeToken<List<Map<String, String>>>(){}.getType());
			List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
			if(null != jsonMapList){
				for(Map<String, String> map : jsonMapList){
					if(null != map){
						String img = map.get("src");
						if(StringUtils.isNotBlank(img)){
							if(img.endsWith("_l.jpg")){
								img = img.replace("_l.jpg", "_s.jpg");
								map.put("src", img);
							}
						}else{
							img="";
						}
						map.put("src", img);
						String info = map.get("info");
						map.put("info", info);
						newList.add(map);
					}
				}
				travelContent.setContent(gson.toJson(newList));
				travelContentService.updateTravelContent(travelContent);
			}
			logger.info("Have Finish Deal Travel ID:{}", travelContent.getTid());
		}
		
		return toError("Yeah");
	}

}
