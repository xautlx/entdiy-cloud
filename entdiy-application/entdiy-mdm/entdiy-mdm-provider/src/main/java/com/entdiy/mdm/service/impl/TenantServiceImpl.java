package com.entdiy.mdm.service.impl;

import com.entdiy.auth.constant.AuthConstant;
import com.entdiy.common.service.BaseService;
import com.entdiy.common.service.BaseServiceImpl;
import com.entdiy.mdm.entity.Tenant;
import com.entdiy.mdm.mapper.TenantMapper;
import com.entdiy.mdm.service.ITenantService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户 服务实现类
 * </p>
 *
 * @author Li Xia
 * @since 2020-08-31
 */
@Service
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, key = "#p0.id", condition = "#p0!=null && #p0.id!=null"),
            @CacheEvict(cacheNames = AuthConstant.CACHE_NAME_TENANT, allEntries = true)
    })
    public boolean saveOrUpdate(Tenant entity) {
        return super.saveOrUpdate(entity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, key = "#p0.id", condition = "#p0!=null && #p0.id!=null"),
            @CacheEvict(cacheNames = AuthConstant.CACHE_NAME_TENANT, key = "#p0.code", condition = "#p0!=null && #p0.id!=null")
    })
    public boolean removeByIdWithFill(Tenant entity) {
        return super.removeByIdWithFill(entity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, allEntries = true),
            @CacheEvict(cacheNames = AuthConstant.CACHE_NAME_TENANT, allEntries = true)
    })
    public int removeByIdsWithFill(Tenant... entities) {
        return super.removeByIdsWithFill(entities);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = BaseService.ENTITY_CACHE_NAME, key = "#p1.id", condition = "#p1!=null && #p1.id!=null"),
            @CacheEvict(cacheNames = AuthConstant.CACHE_NAME_TENANT, allEntries = true)
    })
    public boolean saveOrUpdate(Tenant editDto, Tenant entity) {
        return super.saveOrUpdate(editDto, entity);
    }
}
