package com.lanrenyou.travel.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.ITravelInfoDao;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelInfoService;
import mybatis.framework.core.service.BaseVOService;
import mybatis.framework.core.support.PageIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelInfoServiceImpl extends BaseVOService<TravelInfo> implements ITravelInfoService {
    @Autowired
    private ITravelInfoDao travelInfoDao;

	@Override
	public int addTravelInfo(TravelInfo travelInfo) {
		return travelInfoDao.insert(travelInfo);
	}

	@Override
	public int updateTravelInfo(TravelInfo travelInfo) {
		return travelInfoDao.update(travelInfo);
	}

	@Override
	public int deleteTravelInfo(TravelInfo travelInfo) {
		return travelInfoDao.update2Delete(travelInfo);
	}

	@Override
	public TravelInfo getTravelInfoById(int tid) {
		return travelInfoDao.getById(tid);
	}

	@Override
	public List<TravelInfo> getTravelInfoByIdList(List<Integer> tidList) {
		return travelInfoDao.getByIdList(tidList);
	}

	@Override
	public Map<Integer, TravelInfo> getTravelInfoMapByTidList(
			List<Integer> tidList) {
		List<TravelInfo> travelInfoList = this.getTravelInfoByIdList(tidList);
		if(null == travelInfoList || travelInfoList.size() <= 0){
			return null;
		}
		Map<Integer, TravelInfo> travelInfoMap = new HashMap<Integer, TravelInfo>(travelInfoList.size());
		for(TravelInfo ti : travelInfoList){
			travelInfoMap.put(ti.getId(), ti);
		}
		return travelInfoMap;
	}

	@Override
	public PageIterator<TravelInfo> pageQueryTravelInfoByUid(int uid,
			int pageNo, int pageSize) {
		int totalCount = travelInfoDao.getTravelInfoCountByUid(uid);
		List<TravelInfo> list = null;
		if(totalCount > 0){
			list = travelInfoDao.getTravelInfoListByUid(uid, (pageNo -1) * pageSize, pageSize);
		}
		PageIterator<TravelInfo> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}

	@Override
	public Map<Integer, Integer> getPublishedTravelCntMapByUidList(
			List<Integer> uidList) {
		if(null == uidList || uidList.size() <= 0){
			return null;
		}
		return travelInfoDao.getPublishedTravelCntMapByUidList(uidList);
	}
}