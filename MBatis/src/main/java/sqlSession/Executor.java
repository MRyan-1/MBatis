package sqlSession;

import pojo.Configuration;
import pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @description： Executor
 * @Author MRyan
 * @Date 2021/7/31 23:20
 * @Version 1.0
 */
public interface Executor {

    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException, ClassNotFoundException;

    public Integer update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException;

    public Integer delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException;
}
