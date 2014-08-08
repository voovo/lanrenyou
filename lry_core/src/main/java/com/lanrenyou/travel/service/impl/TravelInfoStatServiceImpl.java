package com.lanrenyou.travel.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.ITravelInfoStatDao;
import com.lanrenyou.travel.model.TravelInfoStat;
import com.lanrenyou.travel.service.ITravelInfoStatService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelInfoStatServiceImpl extends BaseVOService<TravelInfoStat> implements ITravelInfoStatService {
    @Autowired
    private ITravelInfoStatDao travelInfoStatDao;

	@Override
	public int addTravelViewCnt(int tid) {
		TravelInfoStat stat = travelInfoStatDao.getByTid(tid);
		logger.debug("####################{}", stat==null? 0: stat.getId());
		if(null == stat){
			stat = new TravelInfoStat();
			stat.setTid(tid);
			stat.setViewCnt(1);
			stat.setLikeCnt(0);
			return travelInfoStatDao.addTravelInfoStat(stat);
		} else {
			logger.debug("####################ViewCnt{}", stat.getViewCnt());
			stat.setViewCnt(stat.getViewCnt()==null?1:(stat.getViewCnt()+1));
			return travelInfoStatDao.updateTravelInfoStat(stat);
		}
	}

	@Override
	public int addTravelLikeCnt(int tid) {
		TravelInfoStat stat = travelInfoStatDao.getByTid(tid);
		if(null == stat){
			stat = new TravelInfoStat();
			stat.setTid(tid);
			stat.setViewCnt(0);
			stat.setLikeCnt(1);
			return travelInfoStatDao.addTravelInfoStat(stat);
		} else {
			stat.setLikeCnt(stat.getLikeCnt()==null?1:stat.getLikeCnt()+1);
			return travelInfoStatDao.updateTravelInfoStat(stat);
		}
	}

	@Override
	public TravelInfoStat getTravelInfoStatByTid(int tid) {
		return travelInfoStatDao.getByTid(tid);
	}

	@Override
	public Map<Integer, TravelInfoStat> getTravelInfoStatMapByTidList(
			List<Integer> tidList) {
		return travelInfoStatDao.getByTidList(tidList);
	}

	@Override
	public List<Integer> getUpdatedTidListForSearchIndex(Date startTime,
			Date endTime, int offset, int limit) {
		return travelInfoStatDao.getUpdateTidListForSearchIndex(startTime, endTime, offset, limit);
	}
    
}