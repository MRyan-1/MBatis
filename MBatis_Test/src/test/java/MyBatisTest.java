import dao.IUserDao;
import dao.UserDaoImpl;
import org.dom4j.DocumentException;
import org.junit.Test;
import pojo.User;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
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
    public void TEST_QUERY_ONE() throws DocumentException, PropertyVetoException, SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //调用
        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
        IUserDao userDao = UserDaoImpl.getSqlSession().getMapper(IUserDao.class);
        User userByCondition = userDao.findByCondition(user);
        //findByCondition 按照条件查询
        System.out.println("findByCondition 按照条件查询");
        System.out.println(userByCondition.toString());
    }

    @Test
    public void TEST_QUERY_LIST() throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException {
        IUserDao userDao = UserDaoImpl.getSqlSession().getMapper(IUserDao.class);
        List<User> allUserList = userDao.findAll();
        //findAll 查询所有
        System.out.println("findAll 查询所有");
        allUserList.forEach(item -> System.out.println(item.toString()));
    }


    @Test
    public void TEST_UPDATE() throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        IUserDao userDao = UserDaoImpl.getSqlSession().getMapper(IUserDao.class);
        User user = new User();
        user.setId(1);
        user.setUsername("MRyan");
        Integer update = userDao.update(user);
        System.out.println("更新操作");
        System.out.println(update);
        System.out.println("findByCondition 按照条件查询");
        User userByCondition = userDao.findByCondition(user);
        //findByCondition 按照条件查询
        System.out.println("findByCondition 按照条件查询");
        System.out.println(userByCondition.toString());
    }

    @Test
    public void TEST_INSERT() throws PropertyVetoException, DocumentException, ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        IUserDao userDao = UserDaoImpl.getSqlSession().getMapper(IUserDao.class);
        User user = new User();
        user.setId(2);
        user.setUsername("MRyan2");
        Integer add = userDao.insert(user);
        System.out.println("新增操作");
        System.out.println(add);
        System.out.println("findByCondition 按照条件查询");
        User userByCondition = userDao.findByCondition(user);
        //findByCondition 按照条件查询
        System.out.println("findByCondition 按照条件查询");
        System.out.println(userByCondition.toString());
    }

    @Test
    public void TEST_DELETE() throws PropertyVetoException, SQLException, DocumentException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        IUserDao userDao = UserDaoImpl.getSqlSession().getMapper(IUserDao.class);
        User user = new User();
        user.setId(1);
        Integer delete = userDao.delete(user);
        System.out.println("删除操作");
        System.out.println(delete);
        List<User> allUserList = userDao.findAll();
        //findAll 查询所有
        System.out.println("findAll 查询所有");
        allUserList.forEach(item -> System.out.println(item.toString()));
    }
}
