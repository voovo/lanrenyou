package mybatis.framework.core.dao;


import mybatis.framework.core.support.SqlSessionWrapper;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * User: hui.ouyang
 * Date: 2010-4-8
 * Time: 13:53:43
 */
public abstract class Dao {
	@Autowired
	@Qualifier("sqlSessionWrapper")
    SqlSessionWrapper sessionWrapper;

    protected Object selectOne(String statement) {
        return selectOne(statement, null);
    }

    protected Object selectOne(String statement, Object parameter) {
        String operationId = statement.substring(statement.lastIndexOf(".") + 1);
        if (!operationId.equals("get")) {
            List list = selectList(statement, parameter, RowBounds.DEFAULT);
            if (list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        } else {
            return sessionWrapper.selectOne(statement, parameter);
        }
    }

    protected List selectList(String statement) {
        return selectList(statement, null);
    }

    protected List selectList(String statement, Object parameter) {
        return selectList(statement, parameter, RowBounds.DEFAULT);
    }

    protected List selectList(String statement, Object parameter, RowBounds rowBounds) {
        return sessionWrapper.selectList(statement, parameter, rowBounds);
    }

    protected int insert(String statement) {
        return insert(statement, null);
    }

    protected int insert(String statement, Object parameter) {
        return sessionWrapper.insert(statement, parameter);
    }

    protected int update(String statement) {
        return update(statement, null);
    }

    protected int update(String statement, Object parameter) {
        return sessionWrapper.update(statement, parameter);
    }

    protected int delete(String statement) {
        return delete(statement, null);
    }

    protected int delete(String statement, Object parameter) {
        return sessionWrapper.delete(statement, parameter);
    }

}