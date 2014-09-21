package com.lanrenyou.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mybatis.framework.core.support.PageIterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.controller.travel.TravelShowUtil;
import com.lanrenyou.letter.migrate.Migrate;
import com.lanrenyou.travel.model.IndexTravel;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.IIndexTravelService;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoService;
import com.lanrenyou.travel.service.impl.TravelContentServiceImpl;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.util.StringUtil;

@Controller
@RequestMapping("")
public class IndexController  extends BaseController {
	
	@Autowired
	private IIndexTravelService indexTravelService;
	
	@Autowired
	private ITravelInfoService travelInfoService;
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@RequestMapping(value={"/index", "/", ""})
	public ModelAndView index(){
		ModelAndView mav =  new ModelAndView("/index");
		return mav;
	}
	
	@RequestMapping(value="/index_data", method=RequestMethod.GET)
	@ResponseBody
	public String indexData(){
		PageIterator<IndexTravel> pageIter = indexTravelService.pageQueryByTidSrcType(-1, null, 1, 60);
		
		List<Integer> tidList = new ArrayList<Integer>();
		for(IndexTravel it : pageIter.getData()){
			tidList.add(it.getTid());
		}
		
		Map<Integer, TravelInfo> travelInfoMap = travelInfoService.getTravelInfoMapByTidList(tidList);
		if(null == travelInfoMap){
			return null;
		}
		Set<Integer> uidSet = new HashSet<Integer>();
		Map<Integer, String> contentMap = new HashMap<Integer, String>();
		for(Integer tid : tidList){
			TravelInfo travelInfo = travelInfoMap.get(tid);
			uidSet.add(travelInfo.getUid());
			contentMap.put(travelInfo.getId(), travelInfo.getContent());
		}
		
		List<Integer> uidList = new ArrayList<Integer>(uidSet);
		Map<Integer, UserInfo> userInfoMap = userInfoService.getUserInfoMapByUidList(uidList);

		Map<Integer, String> srcUrlMap = getSrcUrlMap(tidList);
		
		List<Map<String, Object>> largePicList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> smallPicList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> narrowPicList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> flatPicList = new ArrayList<Map<String, Object>>();
		
		for(IndexTravel it : pageIter.getData()){
			TravelInfo ti = travelInfoMap.get(it.getTid());
			if(null == ti){
				continue;
			}
			UserInfo ui = userInfoMap.get(ti.getUid());
			if(null == ui){
				continue;
			}
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("title", ti.getTitle());
			itemMap.put("url", "/travel/"+ti.getId());
			itemMap.put("uid", ui.getId());
			itemMap.put("user_name", ui.getName());
			itemMap.put("user_avatar", StringUtil.isBlank(ui.getAvatar())?"":ui.getAvatar());
			if(it.getSrcType() == 'l'){
				itemMap.put("img", srcUrlMap.get(ti.getId())==null?"":(srcUrlMap.get(ti.getId()).replace("_s.jpg", "_l.jpg")));
				largePicList.add(itemMap);
			} else if(it.getSrcType() == 's'){
				itemMap.put("img", srcUrlMap.get(ti.getId())==null?"":srcUrlMap.get(ti.getId()));
				smallPicList.add(itemMap);
			} else if(it.getSrcType() == 'n'){
				itemMap.put("img", srcUrlMap.get(ti.getId())==null?"":(srcUrlMap.get(ti.getId()).replace("_s.jpg", "_n.jpg")));
				narrowPicList.add(itemMap);
			} else if(it.getSrcType() == 'f'){
				itemMap.put("img", srcUrlMap.get(ti.getId())==null?"":(srcUrlMap.get(ti.getId()).replace("_s.jpg", "_f.jpg")));
				flatPicList.add(itemMap);
			}
		}
		
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		returnMap.put("l", largePicList);
		returnMap.put("s", smallPicList);
		returnMap.put("n", narrowPicList);
		returnMap.put("f", flatPicList);
		
		String json = gson.toJson(returnMap);
		logger.info("##########################\n{}", json);
		return json;
		
	}
	
	private Map<Integer, String> getSrcUrlMap(List<Integer> tidList){
		if(null == tidList || tidList.size() <= 0){
			return null;
		}
		Map<Integer, String> srcUrlMap = new HashMap<Integer, String>();
		List<TravelContent> travelContentList = travelContentService.getTravelContentListByTidList(tidList);
		for(TravelContent tc : travelContentList){
			List<Map<String, String>> list = gson.fromJson(tc.getContent(), new TypeToken<List<Map<String, String>>>(){}.getType());
			if(null != list && list.size() >= 1){
				Map<String, String> map = list.get(0);
				if(null != map){
					String img = map.get("src");
					if(StringUtils.isNotBlank(img) && (img.contains("wp-content/uploads") || img.contains("img.lanrenyou.com"))){
						if(!img.endsWith("_s.jpg")){
							img.replace(".jpg", "_s.jpg");
							srcUrlMap.put(tc.getTid(), img);
						} else {
							srcUrlMap.put(tc.getTid(), img);
						}
					}
				}
			}
		}
		return srcUrlMap;
	}

}
