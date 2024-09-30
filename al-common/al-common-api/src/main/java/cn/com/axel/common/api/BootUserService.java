package cn.com.axel.common.api;

import cn.com.axel.common.core.utils.AuthInfoUtils;
import cn.com.axel.common.core.utils.StringUtils;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.api.entity.SsoOrg;
import cn.com.axel.common.oauth.api.entity.UserInfo;
import cn.com.axel.common.oauth.api.entity.UserRole;
import cn.com.axel.common.oauth.api.remote.RemoteUserService;
import cn.com.axel.common.oauth.api.vo.TenantVo;
import cn.com.axel.common.oauth.api.vo.UserInfoVo;
import cn.com.axel.common.oauth.service.SsoUserService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.Set;

/**
 * @description: RPC用户服务单体实现
 * @author: axel
 * @date: 2024/4/16
 */
@Service("remoteUserService")
public class BootUserService implements RemoteUserService {
    @Resource
    SsoUserService ssoUserService;

    @Override
    public Result<UserInfoVo> getUserInfo(String origin, String token) {
        return Result.ok(ssoUserService.getUserInfoAndRoles(AuthInfoUtils.getCurrentUserId(), AuthInfoUtils.getCurrentTenantId()));
    }

    @Override
    public Result<UserInfo> getUserById(String origin, String id) {
        return Result.ok(ssoUserService.getUserByIdNoPwd(id));
    }

    @Override
    public Result<UserInfo> getUserByAccount(String origin, String account) {
        return Result.ok(ssoUserService.getUserByAccountNoPwd(account));
    }

    @Override
    public Result<List<UserRole>> getRoles(String origin, String userId, String tenantId) {
        if (StringUtils.isEmpty(userId)) {
            userId = AuthInfoUtils.getCurrentUserId();
        }
        return Result.ok(ssoUserService.getUserRoles(userId, tenantId));
    }

    @Override
    public Result<Set<String>> getPermissions(String origin, String userId, String tenantId) {
        if (StringUtils.isEmpty(userId)) {
            userId = AuthInfoUtils.getCurrentUserId();
        }
        return Result.ok(ssoUserService.getUserPermissions(userId, tenantId));
    }

    @Override
    public Result<List<TenantVo>> getTenants(String origin, String userId) {
        if (StringUtils.isEmpty(userId)) {
            userId = AuthInfoUtils.getCurrentUserId();
        }
        return Result.ok(ssoUserService.getUserTenants(userId), "获取当前租户列表成功!");
    }

    @Override
    public Result<List<SsoOrg>> getOrgs(String origin, String userId, String direction) {
        return ssoUserService.getOrgs(userId, direction);
    }

    @Override
    public Result<List<String>> getOrgIds(String origin, String userId, String tenantId, String direction) {
        return ssoUserService.getOrgIds(tenantId, userId, direction);
    }

    @Override
    public Result<List<String>> getUserIdsByAccounts(String origin, String accounts) {
        return Result.ok(ssoUserService.getUserIdsByAccounts(List.of(accounts.split(","))), "获取用户id成功");
    }
}
