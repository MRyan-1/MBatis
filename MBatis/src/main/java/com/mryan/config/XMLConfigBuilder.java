package com.mryan.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mryan.io.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.mryan.pojo.Configuration;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @description： XMLConfigBuilder
 * @Author MRyan
 * @Date 2021/7/27 23:41
 * @Version 1.0
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        //使用Dom4j解析配置文件
        Document document = new SAXReader().read(inputStream);
        //<configuration></configuration>
        Element rootElement = document.getRootElement();
        List<Element> elementList = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : elementList) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(dataSource);
        mapperElement(rootElement.selectNodes("//mapper"));
        return configuration;
    }


    /**
     * 解析mapper标签
     *
     * @param mapperElements
     * @throws DocumentException
     * @throws ClassNotFoundException
     */
    private void mapperElement(List<Element> mapperElements) throws DocumentException, ClassNotFoundException {
        //解析mapper.xml 拿到路径
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        for (Element mapperElement : mapperElements) {
            String mapperPath = mapperElement.attributeValue("resource");
            InputStream resourceAsSteam =
                    Resource.getResourceAsSteam(mapperPath);
            xmlMapperBuilder.parse(resourceAsSteam);
        }
    }
}
