package cn.com.axel.gateway.common;

import cn.com.axel.common.core.utils.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @description: 网关通用类
 * @author: axel
 * @date: 2023/1/12 17:41
 */
public class GatewayUtils {
    public static void addHeader(ServerHttpRequest.Builder mutate, String name, String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        mutate.header(name, value);
    }

    public static void removeHeader(ServerHttpRequest.Builder mutate, String name) {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }

}
