package com.entdiy.logger.config;

import ch.qos.logback.access.servlet.TeeFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogbackAccessConfiguration {

    @Bean
    @ConditionalOnProperty(name = "logback.access.enabled", matchIfMissing = false)
    public FilterRegistrationBean requestResponseLogFilter() {
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        TeeFilter filter = new TeeFilter();
        filterRegBean.setFilter(filter);
        filterRegBean.setName("Request/Response Access Logger Filter");
        filterRegBean.setAsyncSupported(Boolean.TRUE);
        return filterRegBean;
    }

}
