package com.mryan.sqlSession;

/**
 * @description： SqlSessionFactory
 * @Author MRyan
 * @Date 2021/7/27 23:39
 * @Version 1.0
 */
public interface SqlSessionFactory {

    public SqlSession openSession();
}
