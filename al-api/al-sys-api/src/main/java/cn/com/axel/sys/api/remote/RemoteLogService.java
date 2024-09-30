package cn.com.axel.sys.api.remote;

import cn.com.axel.common.core.constants.RPCConstants;
import cn.com.axel.common.core.constants.ServiceConstants;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.sys.api.entity.SysLog;
import cn.com.axel.sys.api.fallback.RemoteLogFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author: axel
 * @description: 日志对外服务
 * @date: 2022/9/4
 */
@FeignClient(contextId = "remoteLogService", value = ServiceConstants.SYS_SERVICE, fallbackFactory = RemoteLogFallback.class)
public interface RemoteLogService {
    @PostMapping("/sysLog")
    Result<SysLog> insertLog(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @RequestBody SysLog sysLog);
}
