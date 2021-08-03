package pojo;

import java.io.Serializable;

/**
 * @description： 读取Mapper配置文件接收类
 * @Author MRyan
 * @Date 2021/7/27 23:03
 * @Version 1.0
 */
public class MappedStatement implements Serializable {

    /**
     * id标识
     */
    private String id;

    /**
     * sql语句
     */
    private String sql;

    /**
     * 返回值类型
     */
    private Class<?> resultType;

    /**
     * 参数值类型
     */
    private Class<?> parameterType;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }
}
