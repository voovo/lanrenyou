package com.lanrenyou.travel.service.impl;

import java.util.List;

import com.lanrenyou.travel.dao.IIndexBannerDao;
import com.lanrenyou.travel.model.IndexBanner;
import com.lanrenyou.travel.service.IIndexBannerService;
import mybatis.framework.core.service.BaseVOService;
import mybatis.framework.core.support.PageIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexBannerServiceImpl extends BaseVOService<IndexBanner> implements IIndexBannerService {
    @Autowired
    private IIndexBannerDao indexBannerDao;

	@Override
	public int addIndexBanner(IndexBanner indexBanner) {
		return indexBannerDao.insert(indexBanner);
	}

	@Override
	public int deleteById(int id) {
		return indexBannerDao.deleteById(id);
	}

	@Override
	public int updateIndexBanner(IndexBanner indexBanner) {
		return indexBannerDao.updateIndexBanner(indexBanner);
	}

	@Override
	public PageIterator<IndexBanner> pageQueryByStatus(int isDel, int pageNo, int pageSize) {
		int totalCount = indexBannerDao.getCountByStatus(isDel);
		List<IndexBanner> list = null;
		if(totalCount > 0){
			list = indexBannerDao.getListByStatus(isDel, (pageNo -1) * pageSize, pageSize);
		}
		PageIterator<IndexBanner> pageIterator = PageIterator.createInstance(pageNo, pageSize, totalCount);
		pageIterator.setData(list);
		return pageIterator;
	}

	@Override
	public int updateToDel(int id) {
		return indexBannerDao.updateToDel(id);
	}
}