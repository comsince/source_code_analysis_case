package com.comsince.github;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-9 上午11:28
 **/
public class SimpleJob implements Job {
    String jobname;
    int jobstatus;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(new Date())+" jobname "+jobname+" jobstatus "+jobstatus);
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public int getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(int jobstatus) {
        this.jobstatus = jobstatus;
    }
}
