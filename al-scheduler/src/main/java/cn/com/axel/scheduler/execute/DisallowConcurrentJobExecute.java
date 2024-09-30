package cn.com.axel.scheduler.execute;

import cn.com.axel.common.scheduler.api.entity.JobLog;
import cn.com.axel.scheduler.invoke.BaseInvoke;
import org.quartz.DisallowConcurrentExecution;

import java.util.List;

/**
 * @description: 禁止并发job执行
 * @author: axel
 * @date: 2023/2/7 10:45
 */
@DisallowConcurrentExecution
public class DisallowConcurrentJobExecute extends AbstractJobExecute {
    @Override
    protected <T> void execute(BaseInvoke baseJob, JobLog jobLog, List<T> params) {
        baseJob.run(jobLog, params);
    }
}
