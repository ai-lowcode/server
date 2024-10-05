package cn.com.axel.common.oauth.service;

import cn.com.axel.common.core.enums.TreeDirection;
import cn.com.axel.common.core.web.PageResult;
import cn.com.axel.common.core.web.ReqPage;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.api.entity.SsoOrg;
import cn.com.axel.common.oauth.api.entity.UserInfo;
import cn.com.axel.common.oauth.api.entity.UserRole;
import cn.com.axel.common.oauth.req.ReqOrgUser;
import cn.com.axel.common.oauth.req.ReqSsoOrg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 组织结构表
 * @Author: axel
 * @date: 2022-09-20
 * @Version: V0.0.1
 */
public interface SsoOrgService extends IService<SsoOrg> {
    Result<SsoOrg> insertOrg(SsoOrg ssoOrg);

    Result<SsoOrg> updateOrg(SsoOrg ssoOrg);

    List<SsoOrg> queryOrg(ReqSsoOrg reqSsoOrg);

    Result<Boolean> removeOrg(String id);

    int insertOrgRole(String orgId, List<String> roles);

    int deleteOrgRole(String orgId);

    List<SsoOrg> queryOrgByCode(String fixCode, TreeDirection direction);

    List<SsoOrg> queryOrgById(String id, TreeDirection direction);

    Result<List<String>> queryOrgIdsById(String tenantId, List<String> ids, TreeDirection direction);

    List<UserRole> getOrgRoles(String... orgIds);

    Result<List<SsoOrg>> queryByIds(String ids);

    boolean isTenantOrg(String orgId, String tenantId);

    PageResult<UserInfo> queryUserByCode(String code, ReqOrgUser reqOrgUser, ReqPage reqPage);

    Result<List<String>> getOrgIdsByFixCode(String tenantId, List<String> orgCodes, TreeDirection direction);

    Result<List<String>> getOrgIdsById(String tenantId, List<String> orgIds, TreeDirection direction);
}
