# elastic-job

* [quartz 框架使用](http://www.quartz-scheduler.org/documentation/quartz-2.2.2/tutorials/)
* [zookeeper client curator](https://github.com/apache/curator)

## 任务调度核心框架
从作业的启动初始化开始,主要核心调度还是依赖quartz进行调度,具体调度方式参考quartz的核心用法

## zookeeper注册中心
利用注册中心的持久节点与临时节点特性实现数据存储监听
* 主节点选举

## 任务调度启动
以下为核心类，任务从这里开始执行
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