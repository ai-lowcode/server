package cn.com.axel.common.log.service.impl;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.log.mapper.SysLogMapper;
import cn.com.axel.common.log.service.SysLogService;
import cn.com.axel.sys.api.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description: 系统日志
 * @author: axel
 * @date: 2023-01-08
 * @version: V0.0.1
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public Result<SysLog> insertLog(SysLog sysLog) {
        if (baseMapper.insert(sysLog) > 0) {
            return Result.ok(sysLog, "系统日志-添加成功!");
        }
        return Result.fail(sysLog, "错误:系统日志-添加失败!");
    }
}
