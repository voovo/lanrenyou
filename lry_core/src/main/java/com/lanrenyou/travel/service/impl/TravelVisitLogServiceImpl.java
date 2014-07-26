package com.lanrenyou.travel.service.impl;

import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.ITravelVisitLogDao;
import com.lanrenyou.travel.model.TravelVisitLog;
import com.lanrenyou.travel.service.ITravelInfoStatService;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import mybatis.framework.core.service.BaseVOService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelVisitLogServiceImpl extends BaseVOService<TravelVisitLog> implements ITravelVisitLogService {
    @Autowired
    private ITravelVisitLogDao travelVisitLogDao;
    
    @Autowired
    private ITravelInfoStatService travelInfoStatService;

	@Override
	public int addTravelVisitLog(TravelVisitLog visitLog) {
		int result = travelVisitLogDao.insert(visitLog);
		travelInfoStatService.addTravelViewCnt(visitLog.getTid());
		return result;
	}

	@Override
	public int getVisitCountByTid(int tid) {
		return travelVisitLogDao.getCountByTid(tid);
	}

	@Override
	public Map<Integer, Integer> getVisitCountMapByTidList(List<Integer> tidList) {
		return travelVisitLogDao.getVisitCntMapByTidList(tidList);
	}
	
}