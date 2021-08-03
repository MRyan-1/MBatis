package dao;

import io.Resource;
import org.dom4j.DocumentException;
import pojo.User;
import sqlSession.SqlSession;
import sqlSession.SqlSessionFactory;
import sqlSession.SqlSessionFactoryBuilder;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @description： UserDaoImpl
 * @Author MRyan
 * @Date 2021/8/1 14:24
 * @Version 1.0
 */
public class UserDaoImpl implements IUserDao {

    @Override
    public List<User> findAll() throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, PropertyVetoException, DocumentException {
        return getSqlSession().selectList("user.selectList");
    }


    @Override
    public User findByCondition(User user) throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
        return getSqlSession().selectOne("user.selectList", user);
    }

    @Override
    public Integer update(User user) throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        return getSqlSession().update("user.update", user);
    }

    @Override
    public Integer delete(User user) throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        return getSqlSession().delete("user.delete", user);
    }

    @Override
    public Integer insert(User user) throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        return getSqlSession().delete("user.insert", user);
    }


    public static SqlSession getSqlSession() throws DocumentException, PropertyVetoException, ClassNotFoundException {
        InputStream inputStream = Resource.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }
}
