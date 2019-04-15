# elastic-job



## 概述
分布式任务调度框架源码分析，主要从设计思想分析其大致的实现思路
* 依赖组件  
任何一个框架不是凭空诞生的，有时候其可能依赖其他第三方框架，因此理解其依赖的框架显得至关重要，如果某个框架是依赖其他框架，也即是源码分析依赖三方框架的实现基础，然后再在此基础上进行扩展增加功能。

* 核心功能  
这时一个开源项目的核心功能，也是其借助三方组件构建起来的逻辑依据。核心功能时框架所要实现的最主要，最核心的功能，开发者不需要关注具体实现，只需要关注接口即可
这里也是常说的实现原理

* 对外核心接口  
框架本身可以任由开发者自由使用，不再依赖诸如spring框架，这就是该框架提供的核心编程接口，包括配置接口，核心功能接口

## 源码解析

### 依赖组件
* [quartz 框架使用](http://www.quartz-scheduler.org/documentation/quartz-2.2.2/tutorials/)
* [zookeeper client curator](https://github.com/apache/curator)
#### zookeeper注册中心
利用注册中心的持久节点与临时节点特性实现数据存储监听
* 主节点选举

### 对外核心接口
#### 配置类
这里的配置类主要还是任务job的相关运行的配置，例如job名称，定时调度规则
* JobCoreConfiguration  作业核心配置类
对任务进行分类配置
* SimpleJobConfiguration
* ScriptJobConfiguration
* DataflowJobConfiguration

JobScheduler最终配置类
* LiteJobConfiguration 增加一些参数，包括分片策略

#### 核心功能接口
任务调度框架，主要是在特定事件调用开发这定义的job，一般思路就是需要开发者实现自己的job，以便于定时调度框架等待时间来临之时进行任务调度。基于此思想开发者需要配置job实现类
* JobScheduler 核心启动类 启动任务
api接口,这些接口就是开发者编程接口，实现这里的逻辑，等待框架定时调度
* simpleJob 简单job
* dataflowJob 工作流job
* scriptJob 脚本型作业

### 任务调度核心框架
从作业的启动初始化开始,主要核心调度还是依赖quartz进行调度,具体调度方式参考quartz的核心用法
单个作业的启动，暂停，恢复都是依赖quartz定时框架来实现，elasticjob主要实现如何进行分片，以及如果执行该分片的作业

#### 任务调度启动
以下为核心类，任务从这里开始执行，quartz就是回调job的excute的方法来实现任务调度的，可以说，elastic的核心逻辑就是从这里开始的
关于context的解释，上下文也就是指运行某个任务时，这个任何所需要的核心参数，大多保存在这里，供应用取用，用以实现特定的功能
```java
/**
 * Lite调度作业.
 *
 * @author zhangliang
 */
public final class LiteJob implements Job {
    
    @Setter
    private ElasticJob elasticJob;
    
    @Setter
    private JobFacade jobFacade;
    
    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        JobExecutorFactory.getJobExecutor(elasticJob, jobFacade).execute();
    }
}
```

## zookeeper任务配置信息

```json
{
    "jobName": "javaSimpleJob", 
    "jobClass": "com.comsince.github.job.simple.JavaSimpleJob", 
    "jobType": "SIMPLE", 
    "cron": "0/5 * * * * ?", 
    "shardingTotalCount": 3, 
    "shardingItemParameters": "0=Beijing,1=Shanghai,2=Guangzhou", 
    "jobParameter": "", 
    "failover": false, 
    "misfire": true, 
    "description": "", 
    "jobProperties": {
        "job_exception_handler": "com.dangdang.ddframe.job.executor.handler.impl.DefaultJobExceptionHandler", 
        "executor_service_handler": "com.dangdang.ddframe.job.executor.handler.impl.DefaultExecutorServiceHandler"
    }, 
    "monitorExecution": true, 
    "maxTimeDiffSeconds": -1, 
    "monitorPort": -1, 
    "jobShardingStrategyClass": "", 
    "reconcileIntervalMinutes": 10, 
    "disabled": false, 
    "overwrite": false
}

```

## 作业分片
采用先标记，在下次运行任务的时候，如果有重新分片的情况，由主节点进行重新分片，然后各个节点读取，进而进行任务执行

## 失效转移