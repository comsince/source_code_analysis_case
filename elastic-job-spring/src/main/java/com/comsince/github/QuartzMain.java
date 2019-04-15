package com.comsince.github;

import com.dangdang.ddframe.job.lite.internal.schedule.JobShutdownHookPlugin;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-9 上午11:17
 **/
public class QuartzMain {


    public static void main(String[] args){
        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("SimpleJob", "group") // name "myJob", group "group1"
                .usingJobData("jobname", "Hello World!")
                .usingJobData("jobstatus", 1)
                .build();

        // Trigger the job to run now, and then every 40 seconds
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();

        StdSchedulerFactory factory = new StdSchedulerFactory();
        try {
            factory.initialize(getBaseQuartzProperties());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            Scheduler scheduler = factory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job,trigger);



            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20*1000);
                        //scheduler.triggerJob(new JobKey("SimpleJob","group"));
                        scheduler.pauseAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private static Properties getBaseQuartzProperties() {
        Properties result = new Properties();
        result.put("org.quartz.threadPool.class", org.quartz.simpl.SimpleThreadPool.class.getName());
        result.put("org.quartz.threadPool.threadCount", "1");
        result.put("org.quartz.scheduler.instanceName", "quartzjob");
        result.put("org.quartz.jobStore.misfireThreshold", "1");
        result.put("org.quartz.plugin.shutdownhook.class", JobShutdownHookPlugin.class.getName());
        result.put("org.quartz.plugin.shutdownhook.cleanShutdown", Boolean.TRUE.toString());
        return result;
    }

}
