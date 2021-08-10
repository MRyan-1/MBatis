package com.mryan.sqlSession;

import com.mryan.config.XMLConfigBuilder;
import org.dom4j.DocumentException;
import com.mryan.pojo.Configuration;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @description： SqlSessionFactoryBuilder
 * @Author MRyan
 * @Date 2021/7/27 23:38
 * @Version 1.0
 */
public class SqlSessionFactoryBuilder {


    public SqlSessionFactory build(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        //1. 解析配置文件，将解析内容封装到Configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);
        //SqlSessionFactoryBuilder创建SqlSession
        return new DefaultSqlSessionFactory(configuration);
    }


}
