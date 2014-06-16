package com.lanrenyou.dict.dao.impl;

import java.util.List;

import com.lanrenyou.dict.dao.IDictCityDao;
import com.lanrenyou.dict.model.DictCity;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class DictCityDaoImpl extends BaseDao<DictCity> implements IDictCityDao {

    public DictCityDaoImpl() {
        super(IDictCityDao.class.getName());
    }

	@Override
	public List<DictCity> getAllDictCity() {
		return this.findAll("selectAll");
	}
}