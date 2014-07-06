package mybatis.framework.core.support;

import org.apache.ibatis.exceptions.IbatisException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

public class SqlSessionWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(SqlSessionWrapper.class);

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public Object selectOne(String statement, Object parameter) {
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSession(true);
            return sqlSession.selectOne(statement, parameter);
        } catch (Throwable t) {
            LOG.error("selectOne(" + statement + ") error:" + t.getMessage());
            t.printStackTrace();
            throw new IbatisException("selectOne error:" + statement);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
                LOG.debug("oops, close_session_4_" + statement);
            }
        }
    }

    public List selectList(String statement, Object parameter, RowBounds rowBounds) {
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSession(true);
            return sqlSession.selectList(statement, parameter, rowBounds);
        } catch (Throwable t) {
            LOG.error("selectList(" + statement + ") error:" + t.getMessage());
            t.printStackTrace();
            throw new IbatisException("selectList error:" + statement);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
                LOG.debug("oops, close_session_4_" + statement);
            }
        }
    }

    public int insert(String statement, Object parameter) {
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSession(false);
            return sqlSession.insert(statement, parameter);
        } catch (Throwable t) {
            LOG.error("insert(" + statement + ") error:" + t.getMessage());
            throw new IbatisException("insert error:" + statement);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
                LOG.debug("oops, close_session_4_" + statement);
            }
        }
    }

    public int update(String statement, Object parameter) {
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSession(false);
            return sqlSession.update(statement, parameter);
        } catch (Throwable t) {
            LOG.error("update(" + statement + ") error:" + t.getMessage());
            throw new IbatisException("update error:" + statement);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
                LOG.debug("oops, close_session_4_" + statement);
            }
        }
    }

    public int delete(String statement, Object parameter) {
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSession(false);
            return sqlSession.delete(statement, parameter);
        } catch (Throwable t) {
            LOG.error("delete(" + statement + ") error:" + t.getMessage());
            throw new IbatisException("delete error:" + statement);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
                LOG.debug("oops, close_session_4_" + statement);
            }
        }
    }

    public SqlSession getBatchSqlSession(boolean isReadOnly) {
    	return sqlSessionFactory.openSession(ExecutorType.BATCH, isReadOnly);
    }

    private SqlSession getSqlSession(boolean isRead) {
    	LOG.debug("Sql Session Factory :"+sqlSessionFactory);
    	return sqlSessionFactory.openSession(isRead);
    }
}
