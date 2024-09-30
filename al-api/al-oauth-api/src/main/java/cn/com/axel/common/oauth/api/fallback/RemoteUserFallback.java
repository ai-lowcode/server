package cn.com.axel.common.oauth.api.fallback;

import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.api.entity.SsoOrg;
import cn.com.axel.common.oauth.api.entity.UserInfo;
import cn.com.axel.common.oauth.api.entity.UserRole;
import cn.com.axel.common.oauth.api.remote.RemoteUserService;
import cn.com.axel.common.oauth.api.vo.TenantVo;
import cn.com.axel.common.oauth.api.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author: axel
 * @description: Feign用户接口降级
 * @date: 2021/12/4 0:27
 */
@Component
@Slf4j
public class RemoteUserFallback implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable cause) {
        log.error("用户服务调用失败:{}", cause.getMessage());
        return new RemoteUserService() {
            @Override
            public Result<UserInfoVo> getUserInfo(String origin, String token) {
                return Result.fail("错误:获取用户失败" + cause.getMessage());
            }

            @Override
            public Result<UserInfo> getUserById(String origin, String id) {
                return Result.fail("错误:通过ID获取用户失败" + cause.getMessage());
            }

            @Override
            public Result<UserInfo> getUserByAccount(String origin, String account) {
                return Result.fail("错误:通过账号获取用户失败" + cause.getMessage());
            }

            @Override
            public Result<List<UserRole>> getRoles(String origin, String userId, String clientId) {
                return Result.fail("错误:获取角色信息失败" + cause.getMessage());
            }

            @Override
            public Result<Set<String>> getPermissions(String origin, String userId, String clientId) {
                return Result.fail("错误:获取按钮权限失败" + cause.getMessage());
            }

            @Override
            public Result<List<TenantVo>> getTenants(String origin, String userId) {
                return Result.fail("错误:获取租户失败" + cause.getMessage());
            }

            @Override
            public Result<List<SsoOrg>> getOrgs(String origin, String userId, String direction) {
                return Result.fail("错误:获取用户组织失败" + cause.getMessage());
            }

            @Override
            public Result<List<String>> getOrgIds(String origin, String userId, String tenantId, String direction) {
                return Result.fail("错误:获取用户组织ID失败" + cause.getMessage());
            }

            @Override
            public Result<List<String>> getUserIdsByAccounts(String origin, String accounts) {
                return Result.fail("错误:获取用户ID失败" + cause.getMessage());
            }
        };
    }
}
