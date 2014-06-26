package com.lanrenyou.travel.service.impl;

import com.lanrenyou.travel.dao.ITravelVisitLogDao;
import com.lanrenyou.travel.model.TravelVisitLog;
import com.lanrenyou.travel.service.ITravelVisitLogService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelVisitLogServiceImpl extends BaseVOService<TravelVisitLog> implements ITravelVisitLogService {
    @Autowired
    private ITravelVisitLogDao travelVisitLogDao;

	@Override
	public int addTravelVisitLog(TravelVisitLog visitLog) {
		return travelVisitLogDao.insert(visitLog);
	}
}