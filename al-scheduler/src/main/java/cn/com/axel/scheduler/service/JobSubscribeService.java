package cn.com.axel.scheduler.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.scheduler.entity.JobSubscribe;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @description: 任务订阅表
 * @author: axel
 * @date: 2023-02-20
 * @version: V0.0.1
 */
public interface JobSubscribeService extends IService<JobSubscribe> {

    List<JobSubscribe> getSubscribesByJobId(String jobId);

    List<JobSubscribe> getSubscribesByJobIds(List<String> jobIds);

    int removeSubscribesByJobId(String jobId);

    Result<JobSubscribe> insertJobSubscribe(JobSubscribe jobSubscribe);

    Result<List<JobSubscribe>> insertJobSubscribes(List<JobSubscribe> jobSubscribeList);

    Result<Boolean> setStatus(JobSubscribe jobSubscribe) throws SchedulerException;
}
