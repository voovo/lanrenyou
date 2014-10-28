package com.lanrenyou.travel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanrenyou.travel.dao.IIndexBannerDao;
import com.lanrenyou.travel.model.IndexBanner;
import mybatis.framework.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class IndexBannerDaoImpl extends BaseDao<IndexBanner> implements IIndexBannerDao {

    public IndexBannerDaoImpl() {
        super(IIndexBannerDao.class.getName());
    }

	@Override
	public int insert(IndexBanner indexBanner) {
		if(null == indexBanner){
			return 0;
		}
		return this.doInsert("insert", indexBanner);
	}

	@Override
	public int deleteById(int id) {
		return this.delete("deleteByPrimaryKey", id);
	}

	@Override
	public int updateIndexBanner(IndexBanner indexBanner) {
		if(null == indexBanner || indexBanner.getId() == null){
			return 0;
		}
		return this.doUpdate("updateByPrimaryKey", indexBanner);
	}

	@Override
	public List<IndexBanner> getListByStatus(int isDel, int offset, int limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isDel", isDel);
		params.put("offset", offset);
		params.put("limit", limit);
		return this.findList("getListByStatus", params);
	}

	@Override
	public int getCountByStatus(int isDel) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isDel", isDel);
		return (Integer) this.findOne("getCountByStatus", params);
	}
}