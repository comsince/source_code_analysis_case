# 源码分析案例代码

* __mybatis-exmaple__
* __mybatis-spring__

**NOTE:** 请事先建立号对应的数据库

## Sharding-Jdbc

* __sharding-jdbc__
* __sharding-jdbc-mybatis__
sharding集成使用mybatis框架,主要是构造数据源供mybatis使用

* __sharding-jdbc-mybatis-springboot__
spring 集成mybatis-sharding jdbc

### transaction

* __sharding-jdbc-transaction__
* __sharding-jdbc-mybatis-transaction__
* __sharding-jdbc-mybatis-springboot-transaction__


### t-io
网络框架,原始版本来源于[t-io](https://github.com/tywo45/t-io)

### elastic-job

分布式调度框架，主要采用quartz作为单击任务调度基础，结合 zookeeper的配置托管实现任务分片执行，失效转移等特性


```shell
mvn archetype:generate -DgroupId=com.comsince.github -DartifactId=push-connector-DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```