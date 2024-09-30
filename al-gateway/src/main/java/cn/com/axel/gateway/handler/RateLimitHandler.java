package cn.com.axel.gateway.handler;

import cn.com.axel.gateway.common.ServletUtils;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author: axel
 * @description: 限流回调处理
 * @date: 2022/1/1 12:00
 */
public class RateLimitHandler implements WebExceptionHandler {
    @NotNull
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NotNull Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, ex)
                .flatMap(res -> ServletUtils.webFluxResponseWriter(exchange.getResponse(), "错误:服务器繁忙，请稍候再试！"));
    }
}
