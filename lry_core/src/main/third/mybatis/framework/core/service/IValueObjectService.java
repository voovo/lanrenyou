package mybatis.framework.core.service;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import mybatis.framework.core.model.IValueObject;
import mybatis.framework.core.support.Page;


/**
 * Service接口, 增加基本service方法

 *
 * @param <V>
 */
public interface IValueObjectService<V extends IValueObject> extends IService
{

    /**
     * 根据ID获取对象,此方法使用selectByPrimaryKey作为默认statement
     * @param id  
     * @return
     */
    V findById(Serializable id);

    /**
     * 查询所有
     * 此方法使用selectAll作为默认statement
     * @param statement
     * @return
     */
    List<V> findAll();
    
    /**
     * 根据ID删除,返回影响的行数
     * 此方法使用deleteByPrimaryKey作为默认statement
     * @return
     */
    int doDeleteById(Serializable id);
    
    /**
     * 插入数据
     * 此方法使用insert作为默认statement
     * @param vo
     * @return
     */
    int doInsert(V vo);

    /**
     * 根据主键ID更新数据
     * 此方法使用updateByPrimaryKey作为默认statement
     * @param vo
     * @return
     */
    int doUpdateById(V vo);
    
    /**
     * 分页查询
     * 此方法使用pagedQuery作为默认statement
     * <pre>
     * 注意: 分页查询需要使用两个查询statement, 一个查询数据,一个查询总数
     * 其中总数查询将调用statement + "_C" , 即在参数statement后添加后缀"_C"
     * 如此方法使用"pagedQuery"查询数据,使用"pagedQuery_C"查询总数.
     * </pre>
     * @param pageNo
     * @param pageSize
     * @param parameter
     * @return
     */
    Page pagedQuery(int pageNo, int pageSize, Map<String, Object> parameter);

    /**
     * 批量插入数据
     * 此方法使用insertList作为默认statement
     * @param list
     * @return
     */
	int doInsertList(List<V> list);
	
}
