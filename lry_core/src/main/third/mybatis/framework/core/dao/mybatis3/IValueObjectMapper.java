package mybatis.framework.core.dao.mybatis3;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.dao.IDao;
import mybatis.framework.core.model.IValueObject;
import mybatis.framework.core.support.Page;


/**
 * 
 * <b>调用此接口方法时不需要拼接namespace</b>

 *
 * @param <V>
 */
public interface IValueObjectMapper<V extends IValueObject> extends IDao
{
    
    /**
     * 根据ID获取对象
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
     * @param statement
     * @param id
     * @return
     */
    V findById(String statement, Serializable id);

    /**
     * 查询所有
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
     * @param statement
     * @return
     */
    List<V> findAll(String statement);

    /**
     * 根据ID删除,返回影响的行数
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
     * @param statement
     * @param id
     * @return
     */
    int doDelete(String statement, Serializable id);
    
    /**
     * 插入数据
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
     * @param statement
     * @param vo
     * @return
     */
    int doInsert(String statement, V vo);

    /**
     * 根据主键ID更新数据
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
     * @param statement
     * @param vo
     * @return
     */
    int doUpdate(String statement, V vo);

	/**
     * 分页查询
     * <pre>
     * 注意: 分页查询需要使用两个查询statement, 一个查询数据,一个查询总数
     * 其中总数查询将调用statement + "_C" , 即在参数statement后添加后缀"_C"
     * 
     * <b>调用时不需要拼接namespace</b>
     * </pre>
	 * @param statement
	 * @param pageNo
	 * @param pageSize
	 * @param parameter
	 * @return
	 */
	Page pagedQuery(String statement, int pageNo, int pageSize, Map<String, Object> parameter);
	
	/**
     * 单个查询
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
	 * @param statement
	 * @param parameter
	 * @return
	 */
	Object findOne(String statement, Map<String, Object> parameter);

	/**
	 * 集合查询
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
	 * @param statement
	 * @param parameter
	 * @return
	 */
	List findList(String statement, Map<String, Object> parameter);
	
	/**
     * 根据条件删除,返回影响的行数
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
	 * @param statement
	 * @param parameter
	 * @return
	 */
	int doDelete(String statement, Map<String, Object> parameter);
	
	/**
     * 根据条件更新,返回影响的行数
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
	 * @param statement
	 * @param parameter
	 * @return
	 */
	int doUpdate(String statement, Map<String, Object> parameter);

    /**
     * 批量插入数据
     * <pre>
     * <b>调用时不需要拼接namespace</b>
     * </pre>
     * @param statement
     * @param parameter  一般为list
     * @return
     */
	int doInsertList(String statement, Object parameter);
}
