package sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @description： SqlSession
 * @Author MRyan
 * @Date 2021/7/31 22:48
 * @Version 1.0
 */
public interface SqlSession {

    //*************查询相关*************

    /**
     * 查询所有
     *
     * @param statementId
     * @param params
     * @param <E>
     * @return
     */
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    /**
     * 根据条件查询单个
     *
     * @param statementId
     * @param params
     * @param <T>
     * @return
     */
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    /**
     * 根据条件删除
     *
     * @param statementId
     * @param params
     * @param <T>
     * @return
     */
    public Integer delete(String statementId, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException;

    /**
     * 根据条件更新
     *
     * @param statementId
     * @param params
     * @return
     */
    public Integer update(String statementId, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException;


    /**
     * 采用代理模式来为dao创建接⼝的代理对象 并返回
     *
     * @param mapperClass
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<?> mapperClass);
}
