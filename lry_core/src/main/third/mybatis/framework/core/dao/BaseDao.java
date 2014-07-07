package mybatis.framework.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.model.IValueObject;
import mybatis.framework.core.support.Page;
import mybatis.framework.core.support.PageIterator;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dao接口抽象现实
 */
public abstract class BaseDao<T extends IValueObject> extends Dao implements
		IValueObjectDao<T> {
	protected String NAMESPACE = getClass().getName();

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @param component
	 * @param environment
	 * @param namespace
	 */
	public BaseDao(String namespace) {
		super();
		NAMESPACE = namespace;
	}

	public Page pagedQuery(String statement, int pageNo, int pageSize,
			Map<String, Object> parameter) {
		String count_statement = statement + "_C";
		Integer totalCount = (Integer) super.selectOne(new StringBuilder(
				NAMESPACE).append(".").append(count_statement).toString(),
				parameter);
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
		List list = super.selectList(new StringBuilder(NAMESPACE).append(".")
				.append(statement).toString(), parameter, rb);

		return new Page(startIndex, totalCount, pageSize, list);
	}

	public <E> PageIterator<E> pageIteratorQuery(String statement, int pageNo,
			int pageSize, Map<String, Object> parameter) {
		String count_statement = statement + "_C";
		Integer totalCount = (Integer) super.selectOne(new StringBuilder(
				NAMESPACE).append(".").append(count_statement).toString(),
				parameter);
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
		List<E> list = super.selectList(new StringBuilder(NAMESPACE)
				.append(".").append(statement).toString(), parameter, rb);

		PageIterator<E> page = PageIterator.createInstance(pageNo, pageSize,
				totalCount);
		page.setData(list);
		return page;
	}

	/**
	 * 范围查询
	 * 
	 * @param <E>
	 * @param statement
	 * @param offset
	 *            第一条数据位置
	 * @param limit
	 *            最多返回的数据条数
	 * @param parameter
	 * @return
	 */
	public <E> List<E> findList(String statement, int offset, int limit,
			Object parameter) {
		RowBounds rb = new RowBounds(offset, limit);
		List<E> list = super.selectList(new StringBuilder(NAMESPACE)
				.append(".").append(statement).toString(), parameter, rb);

		return list;
	}

	@Override
	public T findById(String statement, Serializable id) {
		return (T) super.selectOne(new StringBuilder(NAMESPACE).append(".")
				.append(statement).toString(), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(String statement) {

		return super.selectList(new StringBuilder(NAMESPACE).append(".")
				.append(statement).toString());
	}

	@Override
	public int doDelete(String statement, Serializable id) {
		return super.delete(
				new StringBuilder(NAMESPACE).append(".").append(statement)
						.toString(), id);
	}

	public int doInsert(String statement, T vo) {
		int i = super.insert(
				new StringBuilder(NAMESPACE).append(".").append(statement)
						.toString(), vo);
		return i;
	}

	public int doUpdate(String statement, T vo) {
		int i = super.update(
				new StringBuilder(NAMESPACE).append(".").append(statement)
						.toString(), vo);
		return i;
	};

	@Override
	public Object findOne(String statement, Object parameter) {
		return super.selectOne(
				new StringBuilder(NAMESPACE).append(".").append(statement)
						.toString(), parameter);
	}

	@Override
	public List findList(String statement, Object parameter) {
		return super.selectList(new StringBuilder(NAMESPACE).append(".")
				.append(statement).toString(), parameter);
	}

	@Override
	public int doDelete(String statement, Object parameter) {
		return super.delete(
				new StringBuilder(NAMESPACE).append(".").append(statement)
						.toString(), parameter);
	}

	@Override
	public int doUpdate(String statement, Object parameter) {
		return super.update(
				new StringBuilder(NAMESPACE).append(".").append(statement)
						.toString(), parameter);
	}

	@Override
	public int doInsertList(String statement, Object parameter) {
		int i = super.insert(
				new StringBuilder(NAMESPACE).append(".").append(statement)
						.toString(), parameter);
		return i;
	}
}
