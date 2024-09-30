package cn.com.axel.common.oauth.api.remote;

import cn.com.axel.common.core.constants.RPCConstants;
import cn.com.axel.common.core.constants.ServiceConstants;
import cn.com.axel.common.core.web.PageResult;
import cn.com.axel.common.core.web.ReqPage;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.api.entity.SsoOrg;
import cn.com.axel.common.oauth.api.entity.UserInfo;
import cn.com.axel.common.oauth.api.fallback.RemoteOrgFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 组织远程接口
 * @author: axel
 * @date: 2023/5/22 11:28
 */
@FeignClient(contextId = "remoteOrgService", value = ServiceConstants.OAUTH_SERVICE, fallbackFactory = RemoteOrgFallback.class)
public interface RemoteOrgService {

    @GetMapping("/org/{ids}")
    Result<List<SsoOrg>> queryById(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @PathVariable("ids") String ids);

    @GetMapping("/org/code/{code}")
    Result<List<SsoOrg>> queryByFixCode(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @PathVariable("code") String code, @RequestParam("direction") String direction);

    @GetMapping("/org/user/{code}")
    Result<PageResult<UserInfo>> queryUserByCode(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @PathVariable("code") String code, @RequestParam("account") String account, @RequestParam("nickname") String nickname, @RequestParam("phone") String phone, @SpringQueryMap ReqPage reqPage);

    @GetMapping("/org/ids")
    Result<List<String>> getOrgIdsByFixCode(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @RequestParam("tenantId") String tenantId, @RequestParam("codes") String codes, @RequestParam("direction") String direction);

    @GetMapping("/org/ids/byId")
    Result<List<String>> getOrgIdsById(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @RequestParam("tenantId") String tenantId, @RequestParam("orgIds") String orgIds, @RequestParam("direction") String direction);
}
