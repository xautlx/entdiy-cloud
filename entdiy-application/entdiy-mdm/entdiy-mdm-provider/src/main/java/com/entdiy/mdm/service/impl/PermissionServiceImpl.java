package com.entdiy.mdm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.entdiy.common.service.BaseService;
import com.entdiy.common.service.BaseServiceImpl;
import com.entdiy.mdm.entity.Permission;
import com.entdiy.mdm.mapper.PermissionMapper;
import com.entdiy.mdm.service.IPermissionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-22
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    @Cacheable(cacheNames = BaseService.ENTITY_CACHE_NAME, key = BaseService.ENTITY_CACHE_KEY_ALL)
    public List<Permission> findAllCacheable() {
        return list(Wrappers.emptyWrapper());
    }
}
