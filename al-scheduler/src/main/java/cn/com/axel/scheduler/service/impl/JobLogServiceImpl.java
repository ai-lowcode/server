package cn.com.axel.scheduler.service.impl;

import cn.com.axel.common.scheduler.api.entity.JobLog;
import cn.com.axel.scheduler.mapper.JobLogMapper;
import cn.com.axel.scheduler.service.JobLogService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @description: 任务日志
 * @author: axel
 * @date: 2023-02-14
 * @version: V1.3.1
 */
@Service
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

}
