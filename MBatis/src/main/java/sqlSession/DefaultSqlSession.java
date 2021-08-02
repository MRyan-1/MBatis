package sqlSession;

import pojo.Configuration;
import pojo.MapperStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
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
        return (List<E>) simpleExecutor.query(configuration, mapperStatement, params);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1) {
            return (T) objects.get(0);
        } else {
            throw new RuntimeException("The query result is empty or has too many results");
        }
    }


    @Override
    public Integer delete(String statementId, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        return simpleExecutor.update(configuration, mapperStatement, params);
    }

    @Override
    public Integer update(String statementId, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        return simpleExecutor.delete(configuration, mapperStatement, params);
    }


    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理 为Dao接口生成代理对象
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //方法名
                String methodName = method.getName();
                //接口全限定名
                String className = method.getDeclaringClass().getName();
                //statementId:sql语句唯一标识 =namespace.id=接口全限定名.方法名
                String statementId = className + "." + methodName;
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof ParameterizedType) {
                    return selectList(statementId, args);
                }
                MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
                //update
                if (mapperStatement.getSql().startsWith("update")) {
                    return update(statementId, args);
                }
                //delete
                if (mapperStatement.getSql().startsWith("delete")) {
                    return delete(statementId, args);
                }
                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }
}
