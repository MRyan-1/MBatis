package dao;

import org.dom4j.DocumentException;
import pojo.User;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @description： IUserDao
 * @Author MRyan
 * @Date 2021/8/1 14:23
 * @Version 1.0
 */
public interface IUserDao {

    /**
     * 查询所有
     *
     * @return
     */
    public List<User> findAll() throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, PropertyVetoException, DocumentException;

    /**
     * 根据条件查询
     *
     * @param user
     * @return
     */
    public User findByCondition(User user) throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException;
}
