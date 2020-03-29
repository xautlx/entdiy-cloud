package com.entdiy.common.constant;

import lombok.Getter;

public class CurdConstant {

    public enum UriPrefix {

        API("/api"), RPC("/rpc");

        @Getter
        private String prefix;

        UriPrefix(String prefix) {
            this.prefix = prefix;
        }
    }

    public final static long DEFAULT_EXPORT_LIMIT_SIZE = 1000;

    public static final String AuthorizationServerCacheManager = "authorizationServerRedisCacheManager";

    /**
     * @see 'org.springframework.security.access.expression.SecurityExpressionRoot'
     */
    public static final String PreAuthorizePermitAll = "permitAll()";

    public static final String ROLE_DENIED = "ROLE_DENIED";
}
