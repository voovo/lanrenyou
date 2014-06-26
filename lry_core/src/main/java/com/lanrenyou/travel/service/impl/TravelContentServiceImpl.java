package com.lanrenyou.travel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.ITravelContentDao;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.service.ITravelContentService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelContentServiceImpl extends BaseVOService<TravelContent> implements ITravelContentService {
    @Autowired
    private ITravelContentDao travelContentDao;

	@Override
	public int addTravelContent(TravelContent travelContent) {
		return travelContentDao.insert(travelContent);
	}

	@Override
	public int updateTravelContent(TravelContent travelContent) {
		return travelContentDao.update(travelContent);
	}

	@Override
	public TravelContent getTravelContentByTid(int tid) {
		return travelContentDao.getByTid(tid);
	}

	@Override
	public List<TravelContent> getTravelContentListByTidList(
			List<Integer> tidList) {
		return travelContentDao.getByTidList(tidList);
	}

	@Override
	public Map<Integer, TravelContent> getTravelContentMapByTidList(
			List<Integer> tidList) {
		List<TravelContent> travelContentList = this.getTravelContentListByTidList(tidList);
		if(null == travelContentList || travelContentList.size() <= 0){
			return null;
		}
		Map<Integer, TravelContent> travelContentMap = new HashMap<Integer, TravelContent>(travelContentList.size());
		for(TravelContent tc : travelContentList){
			travelContentMap.put(tc.getTid(), tc);
		}
		return travelContentMap;
	}
}