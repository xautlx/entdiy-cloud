package com.entdiy.common.constant;

import java.time.format.DateTimeFormatter;

public class BaseConstant {

    public final static String DEFAULT = "default";
    public final static String ROOT = "root";

    public final static String ROLE_ROOT_TENANT = "ROLE_ROOT_TENANT";
    public final static String ROLE_USER_TENANT = "ROLE_USER_TENANT";

    public final static String AUTH_DATA_TENANT_ID = "tenant_id";
    public final static String AUTH_DATA_USER_ID = "user_id";

    public final static Long DEFAULT_TENANT_ID = 1L;

    public static final String SPRING_CACHE_NAME_PREFIX = "SpringCache:";

    public final static DateTimeFormatter LocalDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter LocalDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * @see org.springframework.security.access.expression.SecurityExpressionRoot
     */
    public static final String PreAuthorizePermitAll = "permitAll()";
}
