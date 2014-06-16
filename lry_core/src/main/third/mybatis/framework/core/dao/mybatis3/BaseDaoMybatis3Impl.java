package mybatis.framework.core.dao.mybatis3;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.dao.IValueObjectDao;
import mybatis.framework.core.model.IValueObject;
import mybatis.framework.core.support.Page;
import mybatis.framework.core.support.PageIterator;
import mybatis.framework.util.SqlSessionDaoSupport;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Dao接口抽象现实
 * 

 * @param <T>
 */
public abstract class BaseDaoMybatis3Impl<T extends IValueObject> extends SqlSessionDaoSupport implements IValueObjectDao<T>
{
	protected String NAMESPACE = getClass().getName();
	protected Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * @param component
	 * @param environment
	 * @param namespace
	 */
	public BaseDaoMybatis3Impl(String component, String environment, String namespace)
	{
		NAMESPACE = namespace;
	}

	public Page pagedQuery(String statement, int pageNo, int pageSize,
			Map<String, Object> parameter)
	{
		String count_statement = statement + "_C";
		Integer totalCount = (Integer) getSqlSession().selectOne(new StringBuilder(NAMESPACE).append(".").append(count_statement).toString(), parameter);
		if (totalCount == null || totalCount < 1)
			return new Page();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		// if (parameter == null)
		// {
		// parameter = new HashMap<String, Object>();
		// }
		// parameter.put("startIndex", startIndex);
		// parameter.put("pageSize", pageSize);
		RowBounds rb = new RowBounds(startIndex, pageSize);
		List list = getSqlSession().selectList(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), parameter, rb);

		return new Page(startIndex, totalCount, pageSize, list);
	}

	public <E> PageIterator<E> pageIteratorQuery(String statement, int pageNo, int pageSize,
			Map<String, Object> parameter)
	{
		String count_statement = statement + "_C";
		Integer totalCount = (Integer) getSqlSession().selectOne(new StringBuilder(NAMESPACE).append(".").append(count_statement).toString(), parameter);
		if (totalCount == null || totalCount < 1)
			return PageIterator.createInstance(0, 0, 0);
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		// if (parameter == null)
		// {
		// parameter = new HashMap<String, Object>();
		// }
		// parameter.put("startIndex", startIndex);
		// parameter.put("pageSize", pageSize);
		RowBounds rb = new RowBounds(startIndex, pageSize);
		List<E> list = getSqlSession().selectList(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), parameter, rb);

		PageIterator<E> page = PageIterator.createInstance(pageNo, pageSize, totalCount);
		page.setData(list);
		return page;
	}

	@Override
	public T findById(String statement, Serializable id)
	{
		return (T) getSqlSession().selectOne(new StringBuilder(NAMESPACE).append(".").append(statement).toString(),
				id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(String statement)
	{

		return getSqlSession().selectList(new StringBuilder(NAMESPACE).append(".").append(statement).toString());
	}

	@Override
	public int doDelete(String statement, Serializable id)
	{
		return getSqlSession().delete(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), id);
	}

	public int doInsert(String statement, T vo)
	{
		int i = getSqlSession().insert(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), vo);
		return i;
	}

	public int doUpdate(String statement, T vo)
	{
		int i = getSqlSession().update(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), vo);
		return i;
	};

	@Override
	public Object findOne(String statement, Object parameter)
	{
		return getSqlSession().selectOne(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), parameter);
	}
	
	@Override
	public List findList(String statement, Object parameter)
	{
		return getSqlSession().selectList(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), parameter);
	}
	
	@Override
	public int doDelete(String statement, Object parameter)
	{
		return getSqlSession().delete(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), parameter);
	}
	
	@Override
	public int doUpdate(String statement, Object parameter)
	{
		return getSqlSession().update(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), parameter);
	}
	
	@Override
	public int doInsertList(String statement, Object parameter)
	{
		int i = getSqlSession().insert(new StringBuilder(NAMESPACE).append(".").append(statement).toString(), parameter);
		return i;
	}
}