package com.lanrenyou.travel.dao.impl;

import com.lanrenyou.travel.dao.ITravelInfoStatDao;
import com.lanrenyou.travel.model.TravelInfoStat;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelInfoStatDaoImpl extends BaseDao<TravelInfoStat> implements ITravelInfoStatDao {

    public TravelInfoStatDaoImpl() {
        super(ITravelInfoStatDao.class.getName());
    }
}