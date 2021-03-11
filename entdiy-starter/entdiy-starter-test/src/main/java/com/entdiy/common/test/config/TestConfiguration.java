package com.entdiy.common.test.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TestConfiguration {

    /**
     * 单元测试使用简化CacheManager以
     */
    @Bean
    @Profile("ut")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
