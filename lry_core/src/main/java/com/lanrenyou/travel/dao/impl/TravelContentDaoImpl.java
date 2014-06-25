package com.lanrenyou.travel.dao.impl;

import com.lanrenyou.travel.dao.ITravelContentDao;
import com.lanrenyou.travel.model.TravelContent;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class TravelContentDaoImpl extends BaseDao<TravelContent> implements ITravelContentDao {

    public TravelContentDaoImpl() {
        super(ITravelContentDao.class.getName());
    }
}