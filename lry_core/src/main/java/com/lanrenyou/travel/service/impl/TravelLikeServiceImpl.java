package com.lanrenyou.travel.service.impl;

import com.lanrenyou.travel.dao.ITravelLikeDao;
import com.lanrenyou.travel.model.TravelLike;
import com.lanrenyou.travel.service.ITravelLikeService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelLikeServiceImpl extends BaseVOService<TravelLike> implements ITravelLikeService {
    @Autowired
    private ITravelLikeDao travelLikeDao;
}