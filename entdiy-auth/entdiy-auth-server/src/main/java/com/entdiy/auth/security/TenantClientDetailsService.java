package com.entdiy.auth.security;

import com.entdiy.auth.constant.AuthConstant;
import com.entdiy.common.exception.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * 基于租户Client信息服务，默认代理调用JdbcClientDetailsService，可以在此基础上扩展
 */
@Slf4j
public class TenantClientDetailsService implements ClientDetailsService {

    private ClientDetailsService delegateClientDetailsService;

    public TenantClientDetailsService(DataSource dataSource) {
        this.delegateClientDetailsService = new JdbcClientDetailsService(dataSource);
    }

    @Override
    @Cacheable(cacheNames = {AuthConstant.CACHE_NAME_CLIENT}, key = "#p0", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails clientDetails = delegateClientDetailsService.loadClientByClientId(clientId);
        Validation.notNull(clientDetails, "Client认证信息无效: {}", clientId);
        return clientDetails;
    }
}
