package cn.com.axel.common.log.service;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.sys.api.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 系统日志
 * @author: axel
 * @date: 2023-01-08
 * @version: V1.3.1
 */
public interface SysLogService extends IService<SysLog> {
    Result<SysLog> insertLog(SysLog sysLog);
}
