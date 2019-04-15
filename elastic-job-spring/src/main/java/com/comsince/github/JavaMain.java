package com.comsince.github;

import com.comsince.github.job.dataflow.JavaDataflowJob;
import com.comsince.github.job.simple.JavaSimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-9 上午10:29
 **/
public class JavaMain {
    private static final int EMBED_ZOOKEEPER_PORT = 2181;

    private static final String ZOOKEEPER_CONNECTION_STRING = "localhost:" + EMBED_ZOOKEEPER_PORT;

    private static final String JOB_NAMESPACE = "elastic-job-example-lite-java";

    // switch to MySQL by yourself
//    private static final String EVENT_RDB_STORAGE_DRIVER = "com.mysql.jdbc.Driver";
//    private static final String EVENT_RDB_STORAGE_URL = "jdbc:mysql://localhost:3306/elastic_job_log";

    private static final String EVENT_RDB_STORAGE_DRIVER = "org.h2.Driver";

    private static final String EVENT_RDB_STORAGE_URL = "jdbc:h2:mem:job_event_storage";

    private static final String EVENT_RDB_STORAGE_USERNAME = "sa";

    private static final String EVENT_RDB_STORAGE_PASSWORD = "";

    // CHECKSTYLE:OFF
    public static void main(final String[] args) throws IOException {
        // CHECKSTYLE:ON
        //EmbedZookeeperServer.start(EMBED_ZOOKEEPER_PORT);
        CoordinatorRegistryCenter regCenter = setUpRegistryCenter();

        System.out.println("javaSimpleJob config "+regCenter.getDirectly("/javaSimpleJob/config"));
        System.out.println("javaSimpleJob children num "+regCenter.getNumChildren("/javaSimpleJob"));
        regCenter.persistEphemeral("/test","testdata");
        regCenter.persistEphemeral("/test1","testdata");



        JobEventConfiguration jobEventConfig = new JobEventRdbConfiguration(setUpEventTraceDataSource());
        setUpSimpleJob(regCenter, jobEventConfig);
        //setUpDataflowJob(regCenter, jobEventConfig);
//        setUpScriptJob(regCenter, jobEventConfig);

//        TreeCache treeCache = (TreeCache) regCenter.getRawCache("/javaSimpleJob");
//        treeCache.getListenable().addListener(new TreeCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
//                System.out.println("receive event "+event.getType());
//            }
//        });
    }

    private static CoordinatorRegistryCenter setUpRegistryCenter() {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(ZOOKEEPER_CONNECTION_STRING, JOB_NAMESPACE);
        CoordinatorRegistryCenter result = new ZookeeperRegistryCenter(zkConfig);
        result.init();
        return result;
    }

    private static DataSource setUpEventTraceDataSource() {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(EVENT_RDB_STORAGE_DRIVER);
        result.setUrl(EVENT_RDB_STORAGE_URL);
        result.setUsername(EVENT_RDB_STORAGE_USERNAME);
        result.setPassword(EVENT_RDB_STORAGE_PASSWORD);
        return result;
    }

    private static void setUpSimpleJob(final CoordinatorRegistryCenter regCenter, final JobEventConfiguration jobEventConfig) {
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder("javaSimpleJob", "0/5 * * * * ?", 3)
                .shardingItemParameters("0=Beijing,1=Shanghai,2=Guangzhou")
                .build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, JavaSimpleJob.class.getCanonicalName());
        new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(simpleJobConfig).build(), jobEventConfig).init();
    }

    private static void setUpDataflowJob(final CoordinatorRegistryCenter regCenter, final JobEventConfiguration jobEventConfig) {
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder("javaDataflowElasticJob", "0/5 * * * * ?", 3).shardingItemParameters("0=Beijing,1=Shanghai,2=Guangzhou").build();
        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(coreConfig, JavaDataflowJob.class.getCanonicalName(), true);
        new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(dataflowJobConfig).build(), jobEventConfig).init();
    }

    private static void setUpScriptJob(final CoordinatorRegistryCenter regCenter, final JobEventConfiguration jobEventConfig) throws IOException {
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder("scriptElasticJob", "0/5 * * * * ?", 3).build();
        ScriptJobConfiguration scriptJobConfig = new ScriptJobConfiguration(coreConfig, buildScriptCommandLine());
        new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(scriptJobConfig).build(), jobEventConfig).init();
    }

    private static String buildScriptCommandLine() throws IOException {
        if (System.getProperties().getProperty("os.name").contains("Windows")) {
            return Paths.get(JavaMain.class.getResource("/script/demo.bat").getPath().substring(1)).toString();
        }
        Path result = Paths.get(JavaMain.class.getResource("/script/demo.sh").getPath());
        Files.setPosixFilePermissions(result, PosixFilePermissions.fromString("rwxr-xr-x"));
        return result.toString();
    }
}
