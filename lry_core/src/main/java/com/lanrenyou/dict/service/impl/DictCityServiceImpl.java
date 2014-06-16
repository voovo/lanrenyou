package com.lanrenyou.dict.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.dict.dao.IDictCityDao;
import com.lanrenyou.dict.model.DictCity;
import com.lanrenyou.dict.service.IDictCityService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictCityServiceImpl extends BaseVOService<DictCity> implements IDictCityService {
    @Autowired
    private IDictCityDao dictCityDao;

	@Override
	public List<DictCity> getAllDictCity() {
		return dictCityDao.getAllDictCity();
	}

	@Override
	public Map<Integer, DictCity> getDictCityIdMap() {
		List<DictCity> dictCityList = this.getAllDictCity();
		if(null == dictCityList || dictCityList.size() <= 0){
			return null;
		}
		Map<Integer, DictCity> idMap = new HashMap<Integer, DictCity>();
		for(DictCity dc : dictCityList){
			idMap.put(dc.getCode(), dc);
		}
		return idMap;
	}

	@Override
	public Map<String, DictCity> getDictCityNameMap() {
		List<DictCity> dictCityList = this.getAllDictCity();
		if(null == dictCityList || dictCityList.size() <= 0){
			return null;
		}
		Map<String, DictCity> nameMap = new HashMap<String, DictCity>();
		for(DictCity dc : dictCityList){
			nameMap.put(dc.getName(), dc);
		}
		return nameMap;
	}
    
}