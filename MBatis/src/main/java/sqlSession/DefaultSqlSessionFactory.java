package sqlSession;

import pojo.Configuration;

/**
 * @description： DefaultSqlSessionFactory
 * @Author MRyan
 * @Date 2021/7/31 22:35
 * @Version 1.0
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
