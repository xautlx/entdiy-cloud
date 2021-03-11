package com.entdiy.auth.constant;

public class AuthConstant {
    public static final String AUTH_SERVER_NAME = "ServiceAuth";
    public static final String AUTH_CONTEXT_PATH = "/auth";
    public final static String FEIGN_CLIENT_URL_KEY = "${feign.client.url.auth:}";

    public final static String CACHE_NAME_CLIENT = "Auth:ClientDetails";
    public final static String CACHE_NAME_TENANT = "Auth:TenantDetails";
    public final static String CACHE_NAME_USER = "Auth:UserDetails";
    public final static String CACHE_NAME_USER_ROLES = "Auth:UserRoles";

    public static final String USERNAME_DATA_SPLIT = "\t";
}
