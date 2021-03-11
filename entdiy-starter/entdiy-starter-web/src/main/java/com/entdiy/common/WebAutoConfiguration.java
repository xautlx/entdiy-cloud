package com.entdiy.common;

import com.entdiy.common.service.ISupportService;
import com.entdiy.common.service.impl.SupportServiceImpl;
import com.entdiy.common.web.AppConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
@EnableConfigurationProperties({AppConfigProperties.class})
public class WebAutoConfiguration {

    @Bean
    public ISupportService iSupportService() {
        return new SupportServiceImpl();
    }

}
