package cn.com.axel.common.core.constants;

/**
 * @author: axel
 * @description: RPC服务常量
 * @date: 2021/12/1 17:14
 */
public class ServiceConstants {
    //单实例类型服务
    public static final String SERVER_BOOT = "boot";
    public static final String OAUTH_SERVICE = "al-oauth";
    public static final String CODE_SERVICE = "al-code";
    public static final String SYS_SERVICE = "al-sys";
    public static final String SCHEDULER_SERVICE = "al-scheduler";
    public static final String STORAGE_SERVICE = "al-storage";

    public static boolean isBoot(String type) {
        return ServiceConstants.SERVER_BOOT.equals(type);
    }
}
