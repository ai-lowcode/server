package cn.com.axel.scheduler.listener;

import cn.com.axel.scheduler.service.ListenerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import jakarta.annotation.Resource;

/**
 * @description: 触发器监听
 * @author: axel
 * @date: 2023/2/7 12:04
 */
@Slf4j
public class AlTriggerListener implements TriggerListener {
    @Resource
    ListenerService listenerService;

    @Override
    public String getName() {
        return "AlTriggerListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        try {
            this.listenerService.saveTriggerFired(jobExecutionContext);
        } catch (SchedulerException e) {
            log.error("triggerFired异常:", e);
        }
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        try {
            return this.listenerService.saveVetoJobExecution(jobExecutionContext);
        } catch (SchedulerException e) {
            log.error("vetoJobExecution异常:", e);
        }
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        this.listenerService.saveTriggerMisfired(trigger);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        try {
            this.listenerService.saveTriggerComplete(jobExecutionContext, completedExecutionInstruction);
        } catch (SchedulerException e) {
            log.error("triggerComplete异常:", e);
        }
    }
}