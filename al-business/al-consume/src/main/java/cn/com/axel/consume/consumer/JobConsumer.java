package cn.com.axel.consume.consumer;

import cn.com.axel.common.core.constants.RPCConstants;
import cn.com.axel.common.scheduler.api.entity.JobLog;
import cn.com.axel.common.scheduler.api.remote.RemoteSchedulerService;
import cn.com.axel.common.scheduler.config.enums.JobStatus;
import cn.com.axel.common.scheduler.config.utils.InvokeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @description: 任务消费端
 * @author: axel
 * @date: 2023/3/1 16:34
 */
@Slf4j
@Service
@RocketMQMessageListener(nameServer = "${rocketmq.consumer.nameServer}", topic = "${rocketmq.consumer.topic}", consumerGroup = "${rocketmq.consumer.group}")
public class JobConsumer implements RocketMQListener<JobLog> {
    @Resource
    RemoteSchedulerService remoteSchedulerService;

    @Override
    public void onMessage(JobLog jobLog) {
        JobStatus jobStatus = JobStatus.执行成功;
        String error = null;
        try {
            List<?> params = InvokeUtils.strParams2Obj(jobLog.getParams());
            Object obj = InvokeUtils.invokeMethod(jobLog.getClassName(), jobLog.getMethodName(), params);
            log.info("返回结果:" + obj);
        } catch (InvocationTargetException e) {
            error = e.getTargetException().getMessage();
            log.error("错误:任务执行异常", e);
            jobStatus = JobStatus.执行失败;
        } catch (Exception e) {
            error = e.getMessage();
            log.error("错误:任务执行异常", e);
            jobStatus = JobStatus.执行失败;
        } finally {
            remoteSchedulerService.callBackStatus(RPCConstants.INNER, jobLog.setStatus(jobStatus.getValue()).setRemark(error));
        }

    }
}
