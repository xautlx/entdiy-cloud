package com.entdiy.common.feign.config;

import com.entdiy.common.feign.FeignRequestInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign 配置注册
 *
 *
 **/
@Configuration
@EnableFeignClients(basePackages = "com.entdiy")
public class FeignAutoConfiguration {

    @Bean
    public Logger.Level feignLog() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
