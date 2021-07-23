package com.entdiy.common.web;

import com.entdiy.common.datascope.aspect.DataScopeAspect;
import com.entdiy.common.log.aspect.LogAspect;
import com.entdiy.common.log.service.AsyncLogService;
import com.entdiy.common.security.aspect.PreAuthorizeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 系统配置
 */
// 开启线程异步执行
@EnableAsync
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
public class WebAutoConfig {

    @Bean
    public PreAuthorizeAspect preAuthorizeAspect() {
        return new PreAuthorizeAspect();
    }

    @Bean
    public DataScopeAspect dataScopeAspect() {
        return new DataScopeAspect();
    }

    @Bean
    public LogAspect LogAspect() {
        return new LogAspect();
    }

    @Bean
    public AsyncLogService asyncLogService() {
        return new AsyncLogService();
    }
}
