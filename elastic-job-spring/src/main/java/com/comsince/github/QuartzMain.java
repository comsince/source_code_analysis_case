package com.comsince.github;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

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
            Scheduler scheduler = factory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job,trigger);



            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2*1000);
                        scheduler.triggerJob(new JobKey("SimpleJob","group"));
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
