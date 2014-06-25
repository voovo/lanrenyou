package com.lanrenyou.travel.dao.impl;

import com.lanrenyou.travel.dao.ITravelInfoDao;
import com.lanrenyou.travel.model.TravelInfo;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelInfoDaoImpl extends BaseDao<TravelInfo> implements ITravelInfoDao {

    public TravelInfoDaoImpl() {
        super(ITravelInfoDao.class.getName());
    }
}