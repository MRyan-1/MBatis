package pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description： 存放解析sqlMapConfig配置文件 接收类
 * @Author MRyan
 * @Date 2021/7/27 23:07
 * @Version 1.0
 */
public class Configuration {

    private DataSource dataSource;

    /**
     * Key:statementId->namespace.id
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
