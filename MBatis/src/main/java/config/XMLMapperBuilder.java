package config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pojo.Configuration;
import pojo.MappedStatement;

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
        String namespace = rootElement.attributeValue("namespace");
        //解析构建查询相关
        buildMapperStatementMap(rootElement, namespace, "//select");
        //解析构建更新相关
        buildMapperStatementMap(rootElement, namespace, "//update");
        //解析构建删除相关
        buildMapperStatementMap(rootElement, namespace, "//delete");
        //解析构建新增相关
        buildMapperStatementMap(rootElement, namespace, "//insert");


    }

    /**
     * 解析Mapper.xml 标签 构建相应MapperStatement
     *
     * @param rootElement
     * @param namespace
     * @param selectNodes
     * @throws ClassNotFoundException
     */
    private void buildMapperStatementMap(Element rootElement, String namespace, String selectNodes) throws ClassNotFoundException {
        List<Element> elementList = rootElement.selectNodes(selectNodes);
        for (Element element : elementList) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(getClassType(parameterType));
            mappedStatement.setSql(sqlText);
            mappedStatement.setResultType(getClassType(resultType));
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            return Class.forName(parameterType);
        }
        return null;
    }
}
