package cn.com.axel.common.oauth.service;

import cn.com.axel.common.core.web.PageResult;
import cn.com.axel.common.core.web.ReqPage;
import cn.com.axel.common.core.web.Result;
import cn.com.axel.common.oauth.api.entity.SsoOrg;
import cn.com.axel.common.oauth.api.entity.UserInfo;
import cn.com.axel.common.oauth.api.entity.UserRole;
import cn.com.axel.common.oauth.api.vo.TenantVo;
import cn.com.axel.common.oauth.api.vo.UserInfoVo;
import cn.com.axel.common.oauth.entity.OnlineUser;
import cn.com.axel.common.oauth.entity.SimpleUserInfo;
import cn.com.axel.common.oauth.entity.SsoUser;
import cn.com.axel.common.oauth.req.ReqSsoUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author: axel
 * @date: 2020/2/13 16:50
 */
public interface SsoUserService extends IService<SsoUser> {
    Result<Boolean> changePassword(String userId, String oldPwd, String newPwd);

    Result<SsoUser> insertUser(SsoUser user);

    Result<SsoUser> updateUser(SsoUser user);

    boolean removeUser(String id);

    SsoUser getUserByAccount(String account);

    UserInfo getUserByAccountNoPwd(String account);

    List<String> getUserIdsByAccounts(List<String> accounts);

    SsoUser getUserById(String userId);

    UserInfo getUserByIdNoPwd(String userId);

    List<UserInfo> getUserList(ReqSsoUser reqSsoUser);

    List<UserRole> getUserRoles(String userId, String tenantId);

    /**
     * 通过用户ID获取按钮权限
     *
     * @param userId 用户id
     * @param tenantId 租户id
     * @return 返回权限集合
     */
    Set<String> getUserPermissions(String userId, String tenantId);

    List<TenantVo> getUserTenants(String userId);

    Result<List<SsoOrg>> getOrgs(String userId, String direction);

    Result<List<String>> getOrgIds(String tenantId, String userId, String direction);

    /**
     * 判断帐号是否存在
     *
     * @param account 帐号
     * @param userId  如果存在userId，排除当前userId
     * @return true存在，false不存在
     */
    boolean isAccountExist(String account, String userId);

    /**
     * 插入用户角色关系
     *
     * @param userId 用户id
     * @param roles 角色id集合
     * @return 插入数量
     */
    int insertUserRole(String userId, List<String> roles);

    /**
     * 插入用户组织关系
     *
     * @param userId 用户id
     * @param orgList 组织id集合
     * @return 返回插入数量
     */
    int insertUserOrg(String userId, List<String> orgList);

    int deleteUserOrg(String userId, String... orgList);

    boolean isExistUserOrg(String userId, String orgId);

    List<SimpleUserInfo> searchUserList(String condition);

    UserInfo getUserInfo(String userId);

    UserInfoVo getUserInfoAndRoles(String userId, String tenantId);

    /**
     * 获取在线用户
     *
     * @return 返回在线用户
     */
    PageResult<OnlineUser> getOnlineUser(ReqPage reqPage);

    /**
     * 解密token
     *
     * @param sid sid
     * @return 返回解密后的sid
     */
    String decryptSid(String sid);
}
