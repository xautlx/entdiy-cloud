package com.entdiy.common.cache;

import com.entdiy.common.auth.AuthDataHolder;
import com.entdiy.common.service.BaseService;
import com.entdiy.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * 业务缓存名称解析器
 *
 * 1，对特定缓存名称动态转换处理
 * 2，缓存名称追加租户分组标识
 */
public class ServiceCacheResolver extends SimpleCacheResolver {

    public ServiceCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        Collection<String> cacheNames = super.getCacheNames(context);
        if (!CollectionUtils.isEmpty(cacheNames)) {
            List<String> parsedCacheNames = Lists.newArrayListWithExpectedSize(cacheNames.size());
            for (String cacheName : cacheNames) {
                //把服务基类的缓存定义特定缓存名称标识动态转换为对应的泛型实体类名称
                //TODO 性能优化
                if (BaseService.ENTITY_CACHE_NAME.equals(cacheName) && context.getTarget() instanceof BaseServiceImpl) {
                    BaseServiceImpl baseServiceImpl = (BaseServiceImpl) context.getTarget();
                    parsedCacheNames.add(processCacheName("Entity:" + baseServiceImpl.getEntityClassName()));
                } else {
                    parsedCacheNames.add(processCacheName(cacheName));
                }
            }
            return parsedCacheNames;
        }
        return cacheNames;
    }

    /**
     * 追加租户分组标识
     *
     * @return
     */
    private String processCacheName(String cacheName) {
        return cacheName + ":T" + AuthDataHolder.get().getTenantId();
    }
}
