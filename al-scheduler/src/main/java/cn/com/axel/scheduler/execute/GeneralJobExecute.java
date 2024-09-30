package cn.com.axel.scheduler.execute;

import cn.com.axel.common.scheduler.api.entity.JobLog;
import cn.com.axel.scheduler.invoke.BaseInvoke;

import java.util.List;

/**
 * @description: 通用job执行
 * @author: axel
 * @date: 2023/2/7 11:01
 */
public class GeneralJobExecute extends AbstractJobExecute {
    @Override
    protected <T> void execute(BaseInvoke baseJob, JobLog jobLog, List<T> params) {
        baseJob.run(jobLog, params);
    }
}
