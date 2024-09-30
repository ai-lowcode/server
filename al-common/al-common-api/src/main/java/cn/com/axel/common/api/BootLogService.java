package cn.com.axel.common.api;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.log.service.SysLogService;
import cn.com.axel.sys.api.entity.SysLog;
import cn.com.axel.sys.api.remote.RemoteLogService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * @description: 日志接口单实例实现
 * @author: axel
 * @date: 2024/4/16
 */
@Service("remoteLogService")
public class BootLogService implements RemoteLogService {
    @Resource
    SysLogService sysLogService;

    @Override
    public Result<SysLog> insertLog(String origin, SysLog sysLog) {
        return sysLogService.insertLog(sysLog);
    }
}
