package com.lanrenyou.travel.service.impl;

import com.lanrenyou.travel.dao.ITravelContentDao;
import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.service.ITravelContentService;
import mybatis.framework.core.service.BaseVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelContentServiceImpl extends BaseVOService<TravelContent> implements ITravelContentService {
    @Autowired
    private ITravelContentDao travelContentDao;
}