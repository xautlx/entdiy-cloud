package com.entdiy.common.config;

import com.entdiy.common.constant.BaseConstant;
import com.entdiy.common.jackson.ExtLocalDateDeserializer;
import com.entdiy.common.jackson.ExtLocalDateTimeDeserializer;
import com.entdiy.common.web.ApplicationContextHolder;
import com.entdiy.common.web.WebExceptionResolver;
import com.entdiy.common.web.databind.resolver.PageableMethodArgumentResolver;
import com.entdiy.common.web.databind.resolver.QueryWrapperMethodAArgumentResolver;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final String baseUrl;

    public WebMvcConfiguration(
            @Value("${springfox.documentation.swagger-ui.base-url:}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
        registry.
                addResourceHandler(baseUrl + "/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(baseUrl + "/swagger-ui/")
                .setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/pet")
                .allowedOrigins("http://editor.swagger.io");
        registry
                .addMapping("/v2/api-docs.*")
                .allowedOrigins("http://editor.swagger.io");
    }

    @Bean
    public WebExceptionResolver exceptionResolver() {
        return new WebExceptionResolver();
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, exceptionResolver());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableMethodArgumentResolver());
        argumentResolvers.add(new QueryWrapperMethodAArgumentResolver());
    }

    /**
     * 1，
     * 2，日期时间类型格式化输出
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Jackson2ObjectMapperBuilderCustomizer customizer = jacksonObjectMapperBuilder ->
                jacksonObjectMapperBuilder
                        //把所有Long型属性转换为字符串序列化输出，以解决Long类型分布式ID属性过长输出到前端JavaScript精度丢失问题
                        //POJO对象中请始终定义为包装类型Long
                        .serializerByType(Long.class, ToStringSerializer.instance)

                        //原生long型则保持数字类型序列化输出，否则已知分页总记录为long返回如果字符串会导致前端分页组件异常
                        //.serializerByType(Long.TYPE, ToStringSerializer.instance)

                        //日期时间类型格式化输出
                        .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(BaseConstant.LocalDateTimeFormatter))
                        .serializerByType(LocalDate.class, new LocalDateSerializer(BaseConstant.LocalDateFormatter))

                        .deserializerByType(LocalDateTime.class, new ExtLocalDateTimeDeserializer(BaseConstant.LocalDateFormatter))
                        .deserializerByType(LocalDate.class, new ExtLocalDateDeserializer(BaseConstant.LocalDateFormatter));
        return customizer;
    }

}
