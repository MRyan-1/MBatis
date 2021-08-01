package config;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pojo.Configuration;
import pojo.MapperStatement;

import java.io.InputStream;
import java.util.List;

/**
 * @description： XMLMapperBuilder
 * @Author MRyan
 * @Date 2021/7/31 14:22
 * @Version 1.0
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析 SQLMapper封装到configuration中MapperStatement
     *
     * @param resourceAsSteam
     */
    public void parse(InputStream resourceAsSteam) throws DocumentException, ClassNotFoundException {
        Document document = new SAXReader().read(resourceAsSteam);
        Element rootElement = document.getRootElement();
        List<Element> elementList = rootElement.selectNodes("//select");
        String namespace = rootElement.attributeValue("namespace");
        for (Element element : elementList) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getTextTrim();
            MapperStatement mapperStatement = new MapperStatement();
            mapperStatement.setId(id);
            mapperStatement.setParameterType(getClassType(parameterType));
            mapperStatement.setSql(sqlText);
            mapperStatement.setResultType(getClassType(resultType));
            String key = namespace + "." + id;
            configuration.getMapperStatementMap().put(key, mapperStatement);
        }
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType != null) {
            return Class.forName(paramterType);
        }
        return null;
    }
}
