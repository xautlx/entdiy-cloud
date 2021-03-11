package com.entdiy.mdm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.entdiy.common.service.BaseService;
import com.entdiy.common.service.BaseServiceImpl;
import com.entdiy.mdm.entity.DictItem;
import com.entdiy.mdm.mapper.DictItemMapper;
import com.entdiy.mdm.service.IDictItemService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典列表数据 服务实现类
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItemMapper, DictItem> implements IDictItemService {

    @Override
    @Cacheable(cacheNames = BaseService.ENTITY_CACHE_NAME, key = BaseService.ENTITY_CACHE_KEY_ALL)
    public List<DictItem> findAllCacheable() {
        return list(Wrappers.emptyWrapper());
    }
}
