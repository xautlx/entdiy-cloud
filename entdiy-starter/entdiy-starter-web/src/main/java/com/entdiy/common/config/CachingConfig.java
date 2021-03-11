package com.entdiy.common.config;

import com.entdiy.common.cache.ServiceCacheResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CachingConfig extends CachingConfigurerSupport {

    @Autowired
    private CacheManager cacheManager;

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new ServiceCacheResolver(cacheManager);
    }

    /**
     * 基于租户的全部对象缓存key生成器
     *
     * @see Cacheable#keyGenerator()
     * @see SimpleKeyGenerator
     */
//    @Bean(BaseService.ENTITY_CACHE_KEY_GENERATOR_ALL)
//    public KeyGenerator tenantAllKeyGenerator() {
//        return (target, method, params) -> {
//            Long tenantId = AuthDataHolder.get().getTenantId();
//            if (tenantId == null) {
//                tenantId = DEFAULT_TENANT_ID;
//            }
//            return tenantId + "(ALL)";
//        };
//    }
}
