package cn.com.axel.common.oauth.scope;

import cn.com.axel.common.core.utils.AuthInfoUtils;
import cn.com.axel.common.oauth.common.DataScopeUtils;
import cn.com.axel.common.core.utils.StringUtils;

/**
 * @description: 租户数据处理
 * @author: axel
 * @date: 2024/4/26
 */
public class TenantDataScopeHandle implements DataScopeHandle {
    private static final String DEFAULT_FIELD = "tenant_id";

    @Override
    public String buildCondition(String fieldName, String[] values) {
        fieldName = StringUtils.isEmpty(fieldName) ? DEFAULT_FIELD : fieldName;
        if (values == null || values.length == 0) {
            //未传值时使用当前租户id
            values = new String[]{AuthInfoUtils.getCurrentTenantId()};
        }
        return DataScopeUtils.buildCondition(fieldName, values);
    }
}
