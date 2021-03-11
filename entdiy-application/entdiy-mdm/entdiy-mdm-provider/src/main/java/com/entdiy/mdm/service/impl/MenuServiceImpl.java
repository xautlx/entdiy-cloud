package com.entdiy.mdm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.entdiy.common.service.BaseService;
import com.entdiy.common.service.BaseServiceImpl;
import com.entdiy.mdm.entity.Menu;
import com.entdiy.mdm.mapper.MenuMapper;
import com.entdiy.mdm.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author Li Xia
 * @since 2020-09-20
 */
@Slf4j
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    @Cacheable(cacheNames = BaseService.ENTITY_CACHE_NAME, key = BaseService.ENTITY_CACHE_KEY_ALL)
    public List<Menu> findAllCacheable() {
        return list(Wrappers.emptyWrapper());
    }
}
