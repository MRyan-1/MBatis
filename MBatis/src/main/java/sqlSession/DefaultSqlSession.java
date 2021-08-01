package sqlSession;

import pojo.Configuration;
import pojo.MapperStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @description： DefaultSqlSession
 * @Author MRyan
 * @Date 2021/7/31 22:50
 * @Version 1.0
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration, mapperStatement, params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1) {
            return (T) objects.get(0);
        } else {
            throw new RuntimeException("查询结果为空/返回结果过多");
        }
    }
}
