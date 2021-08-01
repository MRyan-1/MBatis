# MBatis

#### Introduction
Hand-picked MyBatis simple version framework


#### Perfect the shortcomings of native JDBC
(1) make the database connection pool initialize connection resources

② Extract sql statements into xml configuration pieces

③ make the underlying technologies such as reflection and introspection dynamically map entities and tables into attributes and fields

#### Frame design
Use end:
1. provide the core configuration file:
   SqlMapperConfig.xml-> stores data source information and collects all mapper.xml
2. Mapper.xml -> sql statement configuration information

Frame end:
1. read the configuration file
   After reading, it is stored in memory in the form of stream and converted into javaBean container object storage
   -Configuration: unique identifier for storing basic information of database connection: namespace+"."+id
   -MapperStatement: store basic information of sql statement
2. Parse the configuration file
- SqlSessionFactoryBuilder: build SqlSessionFactory
  Use dom4j to parse the configuration file, and encapsulate the parsed content into the container object
  Create SqlSessionFactory object to generate SqlSession session using factory mode
  -DefaultSqlSession: openSession (): get the implementation class instance object connected by sqlSession
3. SqlSession and implementation class DefaultSession: define the crud operation method of the database
4. Executor interface and implementation class SimpleExecutor implementation class
   -the underlying encapsulation JDBC completes the execution of JDBC code for the operation of database tables
5. Because the implementation class of dao has the problems of duplicate code and hard coding, the proxy mode is adopted to create the proxy object for dao and load and parse the mapper configuration through getMappper

#### Participation and contribution

1. Fork warehouse
2. Create a new Feat_xxx branch
3. Submit the code
4. Create a new Pull Request