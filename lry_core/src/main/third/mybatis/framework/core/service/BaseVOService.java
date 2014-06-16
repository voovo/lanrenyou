package mybatis.framework.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.model.IValueObject;
import mybatis.framework.core.support.Page;


/**
 * Service接口抽象实现
 * 

 * @param <V>
 */
public abstract class BaseVOService<V extends IValueObject> extends BaseService<V> implements
		IValueObjectService<V>
{

	public BaseVOService()
	{
	}

	@Override
	public V findById(Serializable id)
	{
		return getDao().findById("selectByPrimaryKey", id);
	}

	@Override
	public List<V> findAll()
	{
		return getDao().findAll("selectAll");
	}

	@Override
	public int doDeleteById(Serializable id)
	{
		return getDao().doDelete("deleteByPrimaryKey", id);
	}

	@Override
	public int doInsert(V vo)
	{

		return getDao().doInsert("insert", vo);
	};

	@Override
	public int doUpdateById(V vo)
	{

		return getDao().doUpdate("updateByPrimaryKey", vo);
	};
	
	@Override
	public Page pagedQuery(int pageNo, int pageSize, Map<String, Object> parameter)
	{
		return getDao().pagedQuery("pagedQuery", pageNo, pageSize, parameter);
	}

	@Override
	public int doInsertList(List<V> list)
	{
		return getDao().doInsertList("insertList", list);
	}
}
