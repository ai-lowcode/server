package cn.com.axel.common.oauth.scope;

import cn.com.axel.common.core.utils.AuthInfoUtils;
import cn.com.axel.common.core.utils.StringUtils;
import cn.com.axel.common.oauth.common.DataScopeUtils;

/**
 * @description: 用户数据权限限制
 * @author: axel
 * @date: 2024/4/29
 */
public class UserDataScopeHandle implements DataScopeHandle {
    private static final String DEFAULT_FIELD = "user_id";

    @Override
    public String buildCondition(String fieldName, String[] values) {
        fieldName = StringUtils.isEmpty(fieldName) ? DEFAULT_FIELD : fieldName;
        if (values == null || values.length == 0) {
            //未传值时使用当前用户id
            values = new String[]{AuthInfoUtils.getCurrentUserId()};
        } else {
            return fieldName + " in (select id from sso_user where "
                    + DataScopeUtils.buildCondition("account", values) + ")";
        }
        return DataScopeUtils.buildCondition(fieldName, values);
    }
}