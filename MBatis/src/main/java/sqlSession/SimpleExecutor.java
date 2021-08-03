package sqlSession;

import config.BoundSql;
import pojo.Configuration;
import pojo.MappedStatement;
import utils.GenericTokenParser;
import utils.ParameterMapping;
import utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description： SimpleExecutor
 * @Author MRyan
 * @Date 2021/7/31 23:43
 * @Version 1.0
 */
public class SimpleExecutor implements Executor {

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);
        //5、 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        Class<?> resultType = mappedStatement.getResultType();
        ArrayList<T> results = new ArrayList<T>();
        //5、 封装结果返回集
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            T o = (T) resultType.newInstance();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                //属性名
                String columnName = metaData.getColumnName(i);
                //属性值
                Object value = resultSet.getObject(columnName);
                //使用反射 内省 创建属性描述器，为属性⽣成读写⽅法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                //获取写⽅法
                Method writeMethod = propertyDescriptor.getWriteMethod();
                //向类中写⼊值
                writeMethod.invoke(o, value);
            }
            results.add(o);
        }
        return (List<T>) results;
    }


    @Override
    public Integer update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException {
        // 5. 执行sql
        return getPreparedStatement(configuration, mappedStatement, params).executeUpdate();
    }


    @Override
    public Integer delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 5. 执行sql
        return getPreparedStatement(configuration, mappedStatement, params).executeUpdate();
    }


    private PreparedStatement getPreparedStatement(Configuration configuration, MappedStatement mappedStatement, Object... param) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Connection connection = configuration.getDataSource().getConnection();
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        // 3.获取预处理对象：preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        // 4. 设置参数
        //获取到了参数的全路径
        Class<?> parameterType = mappedStatement.getParameterType();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            //反射
            Field declaredField = parameterType.getDeclaredField(content);
            //暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(param[0]);
            preparedStatement.setObject(i + 1, o);
        }
        return preparedStatement;
    }


    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            return Class.forName(parameterType);
        }
        return null;
    }

    /**
     * 对#{}的解析工作
     * 将#{}使用？替换
     * 解析#{}的值进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类，完成占位符解析工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql语句
        String sqlText = genericTokenParser.parse(sql);
        //#{}解析出来的参数名称
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(sqlText, parameterMappingList);
    }
}
