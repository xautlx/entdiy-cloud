package com.entdiy.gateway.config;

import com.entdiy.gateway.handler.ValidateCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Mono;

/**
 * 路由配置信息
 */
@Configuration
public class RouterFunctionConfiguration {
    @Autowired
    private ValidateCodeHandler validateCodeHandler;

    @SuppressWarnings("rawtypes")
    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions
                .route(RequestPredicates.GET("/validate/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), validateCodeHandler)
                //兼容处理SwaggerUI请求两个地址404异常 fixme 待优化处理方案
                .andRoute(RequestPredicates.GET("/csrf").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), serverRequest -> Mono.empty())
                .andRoute(RequestPredicates.GET("/").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), serverRequest -> Mono.empty());
    }
}
