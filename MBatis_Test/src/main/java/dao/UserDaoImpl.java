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
        InputStream inputStream = Resource.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.selectList("user.selectList");
    }

    @Override
    public User findByCondition(User user) throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
        InputStream inputStream = Resource.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.selectOne("user.selectList", user);
    }
}
