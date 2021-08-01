package config;

import utils.ParameterMapping;

import java.util.List;

/**
 * @description： BoundSql
 * @Author MRyan
 * @Date 2021/8/1 0:02
 * @Version 1.0
 */
public class BoundSql {

    /**
     * 解析后的sql语句
     */
    private String sqlText;

    /**
     * 解析出来的参数
     */
    private List<ParameterMapping> parameterMappingList;

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }


    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
