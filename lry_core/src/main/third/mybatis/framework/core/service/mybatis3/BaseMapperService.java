package mybatis.framework.core.service.mybatis3;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.dao.mybatis3.IValueObjectMapper;
import mybatis.framework.core.model.IValueObject;
import mybatis.framework.core.service.IValueObjectService;
import mybatis.framework.core.support.Page;


/**
 * Service接口抽象实现
 * 

 * @param <V>
 */
public abstract class BaseMapperService<V extends IValueObject> implements
		IValueObjectService<V>
{
	protected final IValueObjectMapper<V> getMapper()
	{
		return null;
	}


	@Override
	public V findById(Serializable id)
	{
		return getMapper().findById("selectByPrimaryKey", id);
	}

	@Override
	public List<V> findAll()
	{
		return getMapper().findAll("selectAll");
	}

	@Override
	public int doDeleteById(Serializable id)
	{
		return getMapper().doDelete("deleteByPrimaryKey", id);
	}

	@Override
	public int doInsert(V vo)
	{

		return getMapper().doInsert("insert", vo);
	};

	@Override
	public int doUpdateById(V vo)
	{

		return getMapper().doUpdate("updateByPrimaryKey", vo);
	};
	
	@Override
	public Page pagedQuery(int pageNo, int pageSize, Map<String, Object> parameter)
	{
		return getMapper().pagedQuery("pagedQuery", pageNo, pageSize, parameter);
	}

	@Override
	public int doInsertList(List<V> list)
	{
		return getMapper().doInsertList("insertList", list);
	}
}
