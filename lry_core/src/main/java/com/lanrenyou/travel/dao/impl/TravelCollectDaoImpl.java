package com.lanrenyou.travel.dao.impl;

import com.lanrenyou.travel.dao.ITravelCollectDao;
import com.lanrenyou.travel.model.TravelCollect;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelCollectDaoImpl extends BaseDao<TravelCollect> implements ITravelCollectDao {

    public TravelCollectDaoImpl() {
        super(ITravelCollectDao.class.getName());
    }
}