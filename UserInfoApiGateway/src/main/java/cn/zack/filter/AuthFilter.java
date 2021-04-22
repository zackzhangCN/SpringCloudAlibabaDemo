package cn.zack.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

/**
 * 实现GlobalFilter, 可以自定义网关全局过滤器
 * 实现Ordered接口, 可以自定义过滤器的执行顺序
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 拦截所有不带指定header头 token:testToken的请求
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 取headers中的token头
        List<String> tokenList = exchange.getRequest().getHeaders().get("token");
        // token不对
        if (CollectionUtils.isEmpty(tokenList) || !tokenList.contains("testToken")) {

            ServerHttpResponse httpResponse = exchange.getResponse();
            // 定义错误信息
            HashMap<String, Object> responseData = new HashMap<>();
            responseData.put("code", 403);
            responseData.put("msg", "无效的请求");

            try {
                // 将信息转为json
                byte[] data = new ObjectMapper().writeValueAsBytes(responseData);
                DataBuffer buffer = httpResponse.bufferFactory().wrap(data);
                // 输出错误信息到页面
                httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                httpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return httpResponse.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                log.error("SpringCloudGateway AuthFilter 校验token头发生异常, error: {}", e.toString());
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
