package com.lanrenyou.travel.service.impl;

import java.util.List;

import com.lanrenyou.travel.dao.IIndexTravelDao;
import com.lanrenyou.travel.model.IndexTravel;
import com.lanrenyou.travel.service.IIndexTravelService;
import mybatis.framework.core.service.BaseVOService;
import mybatis.framework.core.support.PageIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexTravelServiceImpl extends BaseVOService<IndexTravel> implements IIndexTravelService {
    @Autowired
    private IIndexTravelDao indexTravelDao;

	@Override
	public int addIndexTravel(IndexTravel indexTravel) {
		return indexTravelDao.insert(indexTravel);
	}

	@Override
	public int deleteByTid(int tid) {
		return indexTravelDao.deleteByTid(tid);
	}

	@Override
	public int deleteById(int id) {
		return indexTravelDao.deleteById(id);
	}

	@Override
	public int updateIndexTravel(IndexTravel indexTravel) {
		return indexTravelDao.updateIndexTravel(indexTravel);
	}

	@Override
	public IndexTravel getByTid(int tid) {
		return indexTravelDao.getByTid(tid);
	}

	@Override
	public PageIterator<IndexTravel> pageQueryByTidSrcType(int tid, Character srcType, int pageNo, int pageSize) {
		int totalCount = indexTravelDao.getCountByTidSrcType(tid, srcType);
		List<IndexTravel> list = null;
		if(totalCount > 0){
			list = indexTravelDao.getIndexTravelListByTidSrcType(tid, srcType, (pageNo -1) * pageSize, pageSize);
		}
		PageIterator<IndexTravel> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}
}