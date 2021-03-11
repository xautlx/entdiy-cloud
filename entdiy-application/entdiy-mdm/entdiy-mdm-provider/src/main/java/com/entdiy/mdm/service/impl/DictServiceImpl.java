package com.entdiy.mdm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.entdiy.common.service.BaseService;
import com.entdiy.common.service.BaseServiceImpl;
import com.entdiy.mdm.entity.Dict;
import com.entdiy.mdm.mapper.DictMapper;
import com.entdiy.mdm.service.IDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典主数据 服务实现类
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
@Slf4j
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements IDictService {

    @Override
    @Cacheable(cacheNames = BaseService.ENTITY_CACHE_NAME, key = BaseService.ENTITY_CACHE_KEY_ALL)
    public List<Dict> findAllCacheable() {
        return list(Wrappers.emptyWrapper());
    }

}
