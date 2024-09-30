package cn.com.axel.oauth.service;

import cn.com.axel.common.core.web.ReqPage;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.api.entity.SsoTenant;
import cn.com.axel.oauth.req.ReqSsoTenant;
import cn.com.axel.common.oauth.api.vo.TenantVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 租户信息表
 * @author: axel
 * @date: 2023-05-31
 * @version: V1.3.1
 */
public interface SsoTenantService extends IService<SsoTenant> {
    List<TenantVo> queryList(ReqSsoTenant reqSsoTenant, ReqPage reqPage);

    TenantVo queryInfo(String id);

    Result<SsoTenant> insertTenant(SsoTenant ssoTenant);

    Result<SsoTenant> updateTenant(SsoTenant ssoTenant);

    Result<Boolean> deleteTenant(String id);

    boolean isTenantMaster(String userId, String tenantId);

    boolean isTenantMasterOrg(String userId, String orgId);

    List<TenantVo> getTenantByRoleCode(String roleCode);
}
