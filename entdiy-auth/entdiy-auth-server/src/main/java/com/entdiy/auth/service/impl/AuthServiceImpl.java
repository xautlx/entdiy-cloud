package com.entdiy.auth.service.impl;

import com.entdiy.auth.constant.AuthConstant;
import com.entdiy.auth.model.TenantAuthModel;
import com.entdiy.auth.model.UserAuthModel;
import com.entdiy.auth.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${auth.tenant-query-sql:" +
            "select id,tenant_name,account_non_locked,valid_start_date,valid_end_date " +
            "from sys_tenant " +
            "where tenant_code=?}")
    private String tenantQuerySQL;

    @Value("${auth.user-query-sql:" +
            "select * " +
            "from auth_user where tenant_id=? and (account_name=?)}")
    private String userQuerySQL;

    private BeanPropertyRowMapper<TenantAuthModel> tenantAuthModelRowMapper = new BeanPropertyRowMapper(TenantAuthModel.class);

    private BeanPropertyRowMapper<UserAuthModel> userAuthModelRowMapper = new BeanPropertyRowMapper(UserAuthModel.class);


    @Override
    @Cacheable(cacheNames = {AuthConstant.CACHE_NAME_TENANT}, key = "#p0", unless = "#result == null")
    public TenantAuthModel findTenantDetail(String tenantCode) {
        return jdbcTemplate.queryForObject(tenantQuerySQL, new Object[]{tenantCode}, tenantAuthModelRowMapper);
    }

    @Override
    @Cacheable(cacheNames = {AuthConstant.CACHE_NAME_USER}, key = "#p0+'-'+#p1", unless = "#result == null")
    public UserAuthModel findUserDetail(Long tenantId, String username) {
        //TODO 兼容手机、邮件登录
        return jdbcTemplate.queryForObject(userQuerySQL, new Object[]{tenantId, username}, userAuthModelRowMapper);
    }

    @Override
    @Cacheable(cacheNames = {AuthConstant.CACHE_NAME_USER_ROLES}, key = "#p0", unless = "#result == null")
    public List<String> findUserRoles(Long userId) {
        return null;
    }
}
