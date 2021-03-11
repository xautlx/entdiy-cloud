package com.entdiy.auth.service;

import com.entdiy.auth.model.TenantAuthModel;
import com.entdiy.auth.model.UserAuthModel;

import java.util.List;

public interface IAuthService {

    TenantAuthModel findTenantDetail(String tenantCode);

    UserAuthModel findUserDetail(Long tenantId, String username);

    List<String> findUserRoles(Long userId);
}
