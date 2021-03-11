package com.entdiy.common.security;

import com.entdiy.common.auth.AuthDataHolder;
import com.entdiy.common.constant.BaseConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class TenantAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        AuthDataHolder.AuthData authData = AuthDataHolder.get();

        Object clientId = map.get("client_id");
        if (clientId != null) {
            authData.setClientId(clientId.toString());

            Object tenantId = map.get(BaseConstant.AUTH_DATA_TENANT_ID);
            if (tenantId != null) {
                authData.setTenantId(Long.valueOf(tenantId.toString()));
            }
            Object userId = map.get(BaseConstant.AUTH_DATA_USER_ID);
            if (userId != null) {
                authData.setUserId(Long.valueOf(userId.toString()));
            }

            Object userName = map.get("user_name");
            if (userName != null) {
                authData.setAccountName(userName.toString());
            }

            Collection<GrantedAuthority> authorities = authentication.getAuthorities();
            if (!CollectionUtils.isEmpty(authorities)) {
                authData.setRoles(authorities.stream().map(one -> one.getAuthority()).collect(Collectors.toList()));
            }
        }

        return authentication;
    }

}
