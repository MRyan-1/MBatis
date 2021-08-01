package sqlSession;

import config.BoundSql;
import pojo.Configuration;
import pojo.MapperStatement;
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
    public <T> List<T> query(Configuration configuration, MapperStatement mapperStatement, Object... parpams) throws SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        //1、 注册驱动，创建连接
        Connection connection = configuration.getDataSource().getConnection();
        //2、 获取Sql语句  针对sql语句#{} 转换为？  并将#{}值进行解析存储
        //例如 select * from user  where id=#{id}  转化select * from user where id=?
        BoundSql boundSql = getBoundSql(mapperStatement.getSql());
        Class<?> paramterType = mapperStatement.getParameterType();
        //3、 获取预处理对象 preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4、 设置参数  获取传⼊参数类型
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            //反射
            Field field = paramterType.getDeclaredField(content);
            field.setAccessible(true);
            //参数的值
            Object o = field.get(parpams[0]);
            //给占位符赋值
            preparedStatement.setObject(i + 1, o);
        }
        //5、 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        Class<?> resultType = mapperStatement.getResultType();
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
