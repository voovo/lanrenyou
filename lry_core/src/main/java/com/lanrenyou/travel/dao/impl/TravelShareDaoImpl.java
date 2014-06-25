package com.lanrenyou.travel.dao.impl;

import com.lanrenyou.travel.dao.ITravelShareDao;
import com.lanrenyou.travel.model.TravelShare;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelShareDaoImpl extends BaseDao<TravelShare> implements ITravelShareDao {

    public TravelShareDaoImpl() {
        super(ITravelShareDao.class.getName());
    }
}