package cn.com.axel.common.oauth.scope;

/**
 * @description: 数据范围处理
 * @author: axel
 * @date: 2024/4/26
 */
public interface DataScopeHandle {
    String buildCondition(String fieldName, String[] values);
}
