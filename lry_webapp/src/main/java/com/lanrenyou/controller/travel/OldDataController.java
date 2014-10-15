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
//		String[] dirPath = {
//				"/ROOT/www/www_8000/wp-content/uploads/2013/06",
//				"/ROOT/www/www_8000/wp-content/uploads/2013/07",
//				"/ROOT/www/www_8000/wp-content/uploads/2013/08",
//				"/ROOT/www/www_8000/wp-content/uploads/2013/09",
//				"/ROOT/www/www_8000/wp-content/uploads/2013/10",
//				"/ROOT/www/www_8000/wp-content/uploads/2013/11",
//				"/ROOT/www/www_8000/wp-content/uploads/2013/12",
//				"/ROOT/www/www_8000/wp-content/uploads/2014/01",
//				"/ROOT/www/www_8000/wp-content/uploads/2014/02",
//				"/ROOT/www/www_8000/wp-content/uploads/2014/03",
//				"/ROOT/www/www_8000/wp-content/uploads/2014/04",
//				"/ROOT/www/www_8000/wp-content/uploads/2014/05",
//				"/ROOT/www/www_8000/wp-content/uploads/2014/06",
//				"/ROOT/www/www_8000/wp-content/uploads/2014/07",
//				"/ROOT/www/www_8000/wp-content/uploads/2014/08",
//				"/ROOT/www/www_8000/wp-content/uploads/2014/09"};
//		for(String dir : dirPath){
//			File file = new File(dir);
//			String[] fileNames = file.list();
			String[] fileNames = {"DSC02813_s.jpg","IMG_4842_s.jpg","IMG_4860_s.jpg","IMG_2802_s.jpg","IMG_2848_s.jpg","IMG_2660_s.jpg","IMG_2662_s.jpg", "IMG_2642_s.jpg","IMG_4918_ss.jpg","IMG_8996_s.jpg","IMG_8991_s.jpg","IMG_8986_s.jpg","IMG_9058_s.jpg","IMG_9114_s.jpg","IMG_9039_s.jpg","IMG_8981_s.jpg","IMG_9037_s.jpg","IMG_9106_s.jpg","IMG_9089_s.jpg","PHH_3610_s.jpg","IMG_8918_s.jpg","PHH_3603_s.jpg","IMG_9175_s.jpg","IMG_9141_s.jpg","IMG_9170_s.jpg","IMG_9368_s.jpg","IMG_9410_s.jpg","IMG_9312_s.jpg","IMG_9341_s.jpg"};
			if(null != fileNames){
				for(String fileName : fileNames){
					if(StringUtils.isNotBlank(fileName)){
						if((fileName.toLowerCase().endsWith(".jpg"))){
							ImageUtils.cropImageForTravel("/ROOT/www/www_8000/wp-content/uploads/2014/05/"+fileName);
						}
					}
				}
			}
//			logger.info("Deal Old Data Have Finish Dir:{}", dir);
//		}

		return toError("执行完毕");
	}
	
//	@RequestMapping("/dealOldTravel")
	public ModelAndView dealOldTravel() throws IOException {
		Migrate m = new Migrate();
		m.importUsers();
		m.importTravel(m.exportPost());
		return toError("执行完毕");
	}
	
//	@RequestMapping("dealTravel")
	public ModelAndView dealTravelInfo() throws IOException {
		List<TravelContent> list = travelContentService.getTravelContentListForSearchIndex(new Date(), 417, 50);
		for(TravelContent travelContent : list){
			String content = travelContent.getContent();
			if(StringUtils.isBlank(content)){
				continue;
			}
			if(content.endsWith("},]")){
				travelContent.setContent(content.replace("},]", "}]"));
				travelContentService.updateTravelContent(travelContent);
			}
			logger.info("Have Finish Deal Travel ID:{}", travelContent.getTid());
		}
		
		return toError("Yeah");
	}

}
