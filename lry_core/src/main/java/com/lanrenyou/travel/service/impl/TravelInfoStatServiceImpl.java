package com.lanrenyou.travel.service.impl;

import com.lanrenyou.travel.dao.ITravelInfoStatDao;
import com.lanrenyou.travel.model.TravelInfoStat;
import com.lanrenyou.travel.service.ITravelInfoStatService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelInfoStatServiceImpl extends BaseVOService<TravelInfoStat> implements ITravelInfoStatService {
    @Autowired
    private ITravelInfoStatDao travelInfoStatDao;
}