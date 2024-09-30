package cn.com.axel.scheduler.listener;

import cn.com.axel.scheduler.service.ListenerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

import jakarta.annotation.Resource;

/**
 * @description: 任务监听
 * @author: axel
 * @date: 2023/2/7 14:31
 */
@Slf4j
public class AlJobListener implements JobListener {
    @Resource
    ListenerService listenerService;

    @Override
    public String getName() {
        return "AlJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        try {
            this.listenerService.saveJobToBeExecuted(jobExecutionContext);
        } catch (SchedulerException e) {
            log.error("jobToBeExecuted:", e);
        }
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        try {
            this.listenerService.saveJobExecutionVetoed(jobExecutionContext);
        } catch (SchedulerException e) {
            log.error("jobExecutionVetoed:", e);
        }
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        try {
            this.listenerService.saveJobWasExecuted(jobExecutionContext, e);
        } catch (SchedulerException ex) {
            log.error("jobWasExecuted:", ex);
        }
    }
}
