package com.entdiy.mdm.service;

import com.entdiy.common.service.BaseService;
import com.entdiy.mdm.entity.DictItem;

import java.util.List;

/**
 * <p>
 * 字典列表数据 服务类
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
public interface IDictItemService extends BaseService<DictItem> {

    List<DictItem> findAllCacheable();
}
