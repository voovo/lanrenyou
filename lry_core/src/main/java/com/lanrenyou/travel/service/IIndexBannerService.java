package com.lanrenyou.travel.service;

import com.lanrenyou.travel.model.IndexBanner;
import mybatis.framework.core.service.IValueObjectService;
import mybatis.framework.core.support.PageIterator;

public interface IIndexBannerService extends IValueObjectService<IndexBanner> {
	
	/**
	 * addIndexBanner	设置首页banner
	 * 
	 * @param indexBanner
	 * @return int
	 * @exception 
	*/
	public int addIndexBanner(IndexBanner indexBanner);
	
	/**
	 * deleteById	根据ID取消首页Banner
	 * 
	 * @param id
	 * @return int
	 * @exception 
	*/
	public int deleteById(int id);
	
	public int updateToDel(int id);
	
	/**
	 * @param indexBanner
	 * @return
	 */
	public int updateIndexBanner(IndexBanner indexBanner);
	
	public PageIterator<IndexBanner> pageQueryByStatus(int isDel, int pageNo, int pageSize);
	
}