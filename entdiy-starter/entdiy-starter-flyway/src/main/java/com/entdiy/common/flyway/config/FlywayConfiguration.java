package com.entdiy.common.flyway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FlywayConfiguration {
    @Bean
    public FlywayConfigurationCustomizer customizeLicense() {
        return configuration -> {
            //强制程序设置禁用clean数据库，避免yaml文件误配置清空数据库
            if (configuration.isCleanDisabled() == false) {
                log.warn("Flyway cleanDisabled=false is disallowed, will force to set true!");
            }
            configuration.cleanDisabled(true);
        };
    }
}
