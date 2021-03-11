package com.entdiy.auth.security;

import com.entdiy.auth.constant.AuthConstant;
import com.entdiy.common.constant.BaseConstant;
import com.entdiy.common.exception.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

import java.util.Map;

public class TenantOAuth2RequestFactory extends DefaultOAuth2RequestFactory {

    public TenantOAuth2RequestFactory(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
    }

    private String getParam(Map<String, String> requestParameters, String name) {
        String val = requestParameters.get(name);
        requestParameters.remove(name);
        return val == null ? "" : val.trim();
    }

    /**
     * 将前端传入的多个参数转换为框架单一参数，以便向后传递处理
     *
     * @param requestParameters
     * @param authenticatedClient
     * @return
     */
    @Override
    public TokenRequest createTokenRequest(Map<String, String> requestParameters, ClientDetails authenticatedClient) {
        String grantType = requestParameters.get("grant_type");

        if ("password".equals(grantType)) {
            String accountName = Validation.notBlank(getParam(requestParameters, "account_name"), "请输入登录账号");
            String tenantCode = getParam(requestParameters, "tenant_code");
            if (StringUtils.isBlank(tenantCode)) {
                tenantCode = BaseConstant.ROOT;
            }

            String username = accountName.trim() + AuthConstant.USERNAME_DATA_SPLIT + tenantCode.trim();
            requestParameters.put("username", username);
        }

        TokenRequest tokenRequest = super.createTokenRequest(requestParameters, authenticatedClient);

        return tokenRequest;
    }
}
