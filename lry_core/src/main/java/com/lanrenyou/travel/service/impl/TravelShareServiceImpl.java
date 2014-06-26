package com.lanrenyou.travel.service.impl;

import com.lanrenyou.travel.dao.ITravelShareDao;
import com.lanrenyou.travel.model.TravelShare;
import com.lanrenyou.travel.service.ITravelShareService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelShareServiceImpl extends BaseVOService<TravelShare> implements ITravelShareService {
    @Autowired
    private ITravelShareDao travelShareDao;

	@Override
	public int addTravelShare(TravelShare travelShare) {
		return travelShareDao.insert(travelShare);
	}

	@Override
	public int getTravelShareCntByTid(int tid) {
		return travelShareDao.getCountByTid(tid);
	}
}