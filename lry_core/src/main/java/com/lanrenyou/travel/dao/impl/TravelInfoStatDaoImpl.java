package com.lanrenyou.travel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.ITravelInfoStatDao;
import com.lanrenyou.travel.model.TravelInfoStat;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelInfoStatDaoImpl extends BaseDao<TravelInfoStat> implements ITravelInfoStatDao {

    public TravelInfoStatDaoImpl() {
        super(ITravelInfoStatDao.class.getName());
    }

	@Override
	public TravelInfoStat getByTid(int tid) {
		return (TravelInfoStat) this.findOne("getByTid", tid);
	}

	@Override
	public Map<Integer, TravelInfoStat> getByTidList(List<Integer> tidList) {
		if(null == tidList || tidList.size() <= 0){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		List<TravelInfoStat> statList = this.findList("getByTidList", params);
		if(null == statList || statList.size() <= 0){
			return null;
		}
		Map<Integer, TravelInfoStat> map = new HashMap<Integer, TravelInfoStat>();
		for(TravelInfoStat stat : statList){
			map.put(stat.getTid(), stat);
		}
		return map;
	}

	@Override
	public int addTravelInfoStat(TravelInfoStat infoStat) {
		if(null == infoStat){
			return 0;
		}
		return this.doInsert("insert", infoStat);
	}

	@Override
	public int updateTravelInfoStat(TravelInfoStat infoStat) {
		if(null == infoStat || (null != infoStat.getId() && infoStat.getId() > 0 )){
			return 0;
		}
		return this.doUpdate("updateByPrimaryKey", infoStat);
	}
}