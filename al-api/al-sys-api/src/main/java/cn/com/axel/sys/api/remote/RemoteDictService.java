package cn.com.axel.sys.api.remote;

import cn.com.axel.common.core.constants.RPCConstants;
import cn.com.axel.common.core.constants.ServiceConstants;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.sys.api.entity.DictItem;
import cn.com.axel.sys.api.fallback.RemoteDictFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @description: 远程字典服务
 * @author: axel
 * @date: 2023/5/18 18:15
 */
@SuppressWarnings("rawtypes")
@FeignClient(contextId = "remoteDictService", value = ServiceConstants.SYS_SERVICE, fallbackFactory = RemoteDictFallback.class)
public interface RemoteDictService {
    @GetMapping("/dictItem/{dictCode}")
    Result<List<DictItem>> queryByCode(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @PathVariable("dictCode") String dictCode);
}
