package com.entdiy.boot.autoconfigure.web;

import com.entdiy.common.web.AppContextProperties;
import com.entdiy.common.web.WebHandlerExceptionResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableConfigurationProperties(AppContextProperties.class)
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public SmokeController smokeController() {
        return new SmokeController();
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, new WebHandlerExceptionResolver());
    }

}