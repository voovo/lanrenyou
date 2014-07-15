package com.lanrenyou.travel.dao;

import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.model.TravelVisitLog;
import mybatis.framework.core.dao.IValueObjectDao;

public interface ITravelVisitLogDao extends IValueObjectDao<TravelVisitLog> {

	public int insert(TravelVisitLog visitLog);

	public int getCountByTid(int tid);

	public Map<Integer, Integer> getVisitCntMapByTidList(List<Integer> tidList);
}