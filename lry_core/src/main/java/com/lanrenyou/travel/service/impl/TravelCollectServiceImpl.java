package com.lanrenyou.travel.service.impl;

import java.util.List;

import com.lanrenyou.travel.dao.ITravelCollectDao;
import com.lanrenyou.travel.model.TravelCollect;
import com.lanrenyou.travel.service.ITravelCollectService;
import mybatis.framework.core.service.BaseVOService;
import mybatis.framework.core.support.PageIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelCollectServiceImpl extends BaseVOService<TravelCollect> implements ITravelCollectService {
    @Autowired
    private ITravelCollectDao travelCollectDao;

	@Override
	public int addTravelCollect(TravelCollect travelCollect) {
		return travelCollectDao.insert(travelCollect);
	}

	@Override
	public int deleteTravelCollect(TravelCollect travelCollect) {
		return travelCollectDao.update2Delete(travelCollect);
	}

	@Override
	public PageIterator<TravelCollect> pageQueryTravelCollectByUid(int uid,
			int pageNo, int pageSize) {
		int totalCount = travelCollectDao.getTravelCollectCountByUid(uid);
		List<TravelCollect> list = null;
		if(totalCount > 0){
			list = travelCollectDao.getTravelCollectListByUid(uid, (pageNo - 1) * pageSize, pageSize);
		}
		PageIterator<TravelCollect> page = PageIterator.createInstance(pageNo, pageSize, totalCount);
		page.setData(list);
		return page;
	}
	
	@Override
	public PageIterator<TravelCollect> pageQueryTravelCollectByTid(int tid,
			int pageNo, int pageSize) {
		int totalCount = travelCollectDao.getTravelCollectCountByTid(tid);
		List<TravelCollect> list = null;
		if(totalCount > 0){
			list = travelCollectDao.getTravelCollectListByTid(tid, (pageNo - 1) * pageSize, pageSize);
		}
		PageIterator<TravelCollect> page = PageIterator.createInstance(pageNo, pageSize, totalCount);
		page.setData(list);
		return page;
	}
}