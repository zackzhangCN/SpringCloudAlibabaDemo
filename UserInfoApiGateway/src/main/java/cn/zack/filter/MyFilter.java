package cn.zack.filter;

import cn.zack.vo.ResponseCodeEnum;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * 实现GlobalFilter, 可以自定义网关全局过滤器
 * 实现Ordered接口, 可以自定义过滤器的执行顺序
 */
@Slf4j
@Component
public class MyFilter implements GlobalFilter, Ordered {

    private static final String CODE = "code";
    private static final String FLAG = "flag";
    private static final String MSG = "msg";
    private static final String DATA = "data";

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
        ServerHttpResponse httpResponse = exchange.getResponse();
        // token不对, 直接自定义返回
        if (CollectionUtils.isEmpty(tokenList) || !tokenList.contains("testToken")) {
            // 定义错误信息
            HashMap<String, Object> responseData = new HashMap<>();
            responseData.put(CODE, ResponseCodeEnum.FORBIDDEN.getCode());
            responseData.put(FLAG, false);
            responseData.put(MSG, ResponseCodeEnum.FORBIDDEN.getMessage());
            responseData.put(DATA, "{}");

            try {
                // 将信息转为json
                byte[] data = new ObjectMapper().writeValueAsBytes(responseData);
                DataBuffer buffer = httpResponse.bufferFactory().wrap(data);
                // 输出错误信息到页面
                httpResponse.setStatusCode(HttpStatus.OK);
                httpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return httpResponse.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                log.error("SpringCloudGateway MyFilter 校验token头发生异常, error: {}", e.toString());
            }
        }
        // token没问题, 正常处理业务, 拿到响应报文
        DataBufferFactory bufferFactory = httpResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(httpResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        String responseBody = new String(content, StandardCharsets.UTF_8);
                        responseBody = responseHandle(responseBody);
                        byte[] uppedContent = new String(responseBody.getBytes(), StandardCharsets.UTF_8).getBytes();
                        httpResponse.getHeaders().setContentLength(uppedContent.length);
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    /**
     * 返回值必须<-1
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -2;
    }

    /**
     * 请求成功, 响应报文处理
     *
     * @param responseData
     * @return
     */
    public String responseHandle(String responseData) {
        JSONObject responseJson = new JSONObject();
        responseJson.put(CODE, ResponseCodeEnum.OK.getCode());
        responseJson.put(MSG, ResponseCodeEnum.OK.getMessage());
        responseJson.put(FLAG, true);
        responseJson.put(DATA, JSONObject.parse(responseData));
        return responseJson.toJSONString();
    }
}