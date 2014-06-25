package com.lanrenyou.travel.service.impl;

import com.lanrenyou.travel.dao.ITravelInfoDao;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelInfoService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelInfoServiceImpl extends BaseVOService<TravelInfo> implements ITravelInfoService {
    @Autowired
    private ITravelInfoDao travelInfoDao;
}