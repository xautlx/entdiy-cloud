package com.entdiy.boot.autoconfigure.web;

import com.entdiy.common.web.ApplicationContextHolder;
import com.entdiy.common.web.WebHandlerExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }

    @Bean
    public SmokeController smokeController() {
        return new SmokeController();
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, new WebHandlerExceptionResolver());
    }

}