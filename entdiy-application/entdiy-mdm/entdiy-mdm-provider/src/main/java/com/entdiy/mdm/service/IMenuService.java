package com.entdiy.mdm.service;

import com.entdiy.common.service.BaseService;
import com.entdiy.mdm.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author Li Xia
 * @since 2020-09-20
 */
public interface IMenuService extends BaseService<Menu> {

    List<Menu> findAllCacheable();
}
