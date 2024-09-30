package cn.com.axel.common.oauth.common;

import cn.com.axel.common.oauth.scope.DataScopeHandle;
import cn.com.axel.common.oauth.scope.OrgDataScopeHandle;
import cn.com.axel.common.oauth.scope.RoleDataScopeHandle;
import cn.com.axel.common.oauth.scope.TenantDataScopeHandle;
import cn.com.axel.common.oauth.scope.UserDataScopeHandle;
import lombok.Getter;

/**
 * @description: 数据范围
 * @author: axel
 * @date: 2024/4/26
 */
@Getter
public enum DataScopeType {
    None(-1, null),
    //租户
    Tenant(0, new TenantDataScopeHandle()),
    //用户
    User(1, new UserDataScopeHandle()),
    //角色
    Role(2, new RoleDataScopeHandle()),
    //组织
    Org(3, new OrgDataScopeHandle());

    private final int value;
    private final DataScopeHandle handle;

    DataScopeType(int value, DataScopeHandle handle) {
        this.value = value;
        this.handle = handle;
    }

}
