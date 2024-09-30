package cn.com.axel.scheduler.invoke;

import cn.com.axel.common.scheduler.api.entity.JobLog;

import java.util.List;

/**
 * @description: 基础任务
 * @author: axel
 * @date: 2023/2/13 17:41
 */
public interface BaseInvoke {
    <T> Object run(JobLog jobLog, List<T> params);
}
