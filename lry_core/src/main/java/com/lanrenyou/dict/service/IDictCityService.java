package com.lanrenyou.dict.service;

import java.util.List;
import java.util.Map;

import com.lanrenyou.dict.model.DictCity;
import mybatis.framework.core.service.IValueObjectService;

public interface IDictCityService extends IValueObjectService<DictCity> {
	
	public List<DictCity> getAllDictCity();
	
	public Map<Integer, DictCity> getDictCityIdMap();
	
	public Map<String, DictCity> getDictCityNameMap();
}