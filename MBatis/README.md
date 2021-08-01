# MBatis

#### 介绍
手撸MyBatis简易版框架


#### 完善了原生JDBC的缺点
①使⽤数据库连接池初始化连接资源

②将sql语句抽取到xml配置⽂件中

③使⽤反射、内省等底层技术，⾃动将实体与表进⾏属性与字段的⾃动映射

#### 框架设计
使用端：
1. 提供核心配置文件:
sqlMapperConfig.xml -> 存放数据源信息 并集合所有mapper.xml
2. Mapper.xml -> sql语句配置信息

框架端：
1. 读取配置文件 
读取后以流的形式存放在内存中并转换javaBean容器对象存储
    - Configuration: 存放数据库连接基本信息 唯一标识：namespace+"."+id
    - MapperStatement: 存放sql语句基本信息
2. 解析配置文件
    - SqlSessionFactoryBuilder: build SqlSessionFactory
    利用dom4j解析配置文件，将解析内容封装到容器对象
      创建SqlSessionFactory对象 利用工厂模式生成SqlSession会话
    - DefaultSqlSession: openSession() : 获取sqlSession接⼝的实现类实例对象
3. SqlSession接⼝及实现类DefaultSession： 定义数据库crud操作⽅法
4. Executor接口和实现类SimpleExecutor实现类  
    - 底层封装JDBC完成对数据库表的操作 执行JDBC代码
5. 由于dao的实现类存在重复代码和硬编码问题，所以采用代理模式来为dao创建接⼝的代理对象 并通过getMappper加载并解析mapper配置

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

