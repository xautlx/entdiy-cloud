package com.entdiy.mdm.service;

import com.entdiy.common.service.BaseService;
import com.entdiy.mdm.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author Li Xia
 * @since 2020-10-22
 */
public interface IPermissionService extends BaseService<Permission> {
    List<Permission> findAllCacheable();
}
