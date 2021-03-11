package com.entdiy.mdm.service;

import com.entdiy.common.service.BaseService;
import com.entdiy.mdm.entity.Dict;

import java.util.List;

/**
 * <p>
 * 字典主数据 服务类
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-02
 */
public interface IDictService extends BaseService<Dict> {

    List<Dict> findAllCacheable();
}
