package com.lanrenyou.travel.service.impl;

import com.lanrenyou.travel.dao.ITravelCollectDao;
import com.lanrenyou.travel.model.TravelCollect;
import com.lanrenyou.travel.service.ITravelCollectService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelCollectServiceImpl extends BaseVOService<TravelCollect> implements ITravelCollectService {
    @Autowired
    private ITravelCollectDao travelCollectDao;
}