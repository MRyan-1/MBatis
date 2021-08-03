package sqlSession;

import config.BoundSql;
import pojo.Configuration;
import pojo.MappedStatement;
import utils.GenericTokenParser;
import utils.ParameterMapping;
import utils.ParameterMappingTokenHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @description： BaseExecutor
 * @Author MRyan
 * @Date 2021/8/3 18:53
 * @Version 1.0
 */
public abstract class BaseExecutor implements Executor {


    protected PreparedStatement getPreparedStatement(Configuration configuration, MappedStatement mappedStatement, Object... param) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Connection connection = configuration.getDataSource().getConnection();
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        // 1.获取预处理对象：preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        // 2. 设置参数
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
