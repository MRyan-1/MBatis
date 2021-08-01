package sqlSession;

import pojo.Configuration;
import pojo.MapperStatement;

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

    public <T> List<T> query(Configuration configuration, MapperStatement mapperStatement, Object... parpams) throws SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException, ClassNotFoundException;

}
