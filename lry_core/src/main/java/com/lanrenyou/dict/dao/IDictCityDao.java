package com.lanrenyou.dict.dao;

import java.util.List;

import com.lanrenyou.dict.model.DictCity;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IDictCityDao extends IValueObjectDao<DictCity> {
	/**
	 * getAllDictCity	获取所有的城市字典数据
	 * @return List<DictCity>
	 * @exception 
	*/
	public List<DictCity> getAllDictCity();
}