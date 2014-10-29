package com.lanrenyou.travel.dao;

import java.util.List;

import com.lanrenyou.travel.model.IndexBanner;
import mybatis.framework.core.dao.IValueObjectDao;

public interface IIndexBannerDao extends IValueObjectDao<IndexBanner> {

	/**
	 * insert	插入
	 * 
	 * @param indexBanner
	 * @return int
	 * @exception 
	*/
	public int insert(IndexBanner indexBanner);

	/**
	 * deleteById	删除首页中的游记
	 * 
	 * @param id
	 * @return int
	 * @exception 
	*/
	public int deleteById(int id);
	
	/**
	 * 更新首页Banner排序
	 * @param indexBanner
	 * @return
	 */
	public int updateIndexBanner(IndexBanner indexBanner);

	/**
	 * getIndexBannerList	根据状态分批查询Banner
	 * 
	 * @param offset
	 * @param limit
	 * @return List<IndexBanner>
	 * @exception 
	*/
	public List<IndexBanner> getListByStatus(int isDel,int offset, int limit);

	public int getCountByStatus(int isDel);

	public int updateToDel(int id);

}