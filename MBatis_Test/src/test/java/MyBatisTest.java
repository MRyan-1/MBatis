
import dao.IUserDao;
import io.Resource;
import org.dom4j.DocumentException;
import org.junit.Test;
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
 * @description： 测试类
 * @Author MRyan
 * @Date 2021/7/27 22:41
 * @Version 1.0
 */
public class MyBatisTest {

    @Test
    public void test() throws DocumentException, PropertyVetoException, SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        InputStream inputStream = Resource.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //调用
        User user = new User();
        user.setId(2);
        user.setUsername("MRyan");
       /*  List<User> userList = sqlSession.selectList("user.selectList", user);
        for (User user1 : userList) {
            System.out.println(user1.toString());
        }*/
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> allUserList = userDao.findAll();
        User userByCondition = userDao.findByCondition(user);
        //findAll 查询所有
        System.out.println("findAll 查询所有");
        allUserList.forEach(item -> System.out.println(item.toString()));
        //findByCondition 按照条件查询
        System.out.println("findByCondition 按照条件查询");
        System.out.println(userByCondition.toString());
    }


}
