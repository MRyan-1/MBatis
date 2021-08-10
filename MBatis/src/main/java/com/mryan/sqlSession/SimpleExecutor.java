package com.mryan.sqlSession;

import com.mryan.pojo.Configuration;
import com.mryan.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description： SimpleExecutor
 * @Author MRyan
 * @Date 2021/7/31 23:43
 * @Version 1.0
 */
public class SimpleExecutor extends BaseExecutor {

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);
        ResultSet resultSet = preparedStatement.executeQuery();
        Class<?> resultType = mappedStatement.getResultType();
        ArrayList<T> results = new ArrayList<T>();
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
        return getPreparedStatement(configuration, mappedStatement, params).executeUpdate();
    }


    @Override
    public Integer delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getPreparedStatement(configuration, mappedStatement, params).executeUpdate();
    }

    @Override
    public Integer add(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException {
        return getPreparedStatement(configuration, mappedStatement, params).executeUpdate();
    }


}
