package com.lanrenyou.travel.dao;

import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.model.TravelInfoStat;
import mybatis.framework.core.dao.IValueObjectDao;

public interface ITravelInfoStatDao extends IValueObjectDao<TravelInfoStat> {
	
	public TravelInfoStat getByTid(int tid);
	
	public Map<Integer, TravelInfoStat> getByTidList(List<Integer> tidList);
	
	public int addTravelInfoStat(TravelInfoStat infoStat);
	
	public int updateTravelInfoStat(TravelInfoStat infoStat);
	
}