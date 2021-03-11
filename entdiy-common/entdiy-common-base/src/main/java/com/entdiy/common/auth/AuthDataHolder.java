package com.entdiy.common.auth;

import com.entdiy.common.constant.BaseConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AuthDataHolder {
    private static final ThreadLocal<AuthData> dataThreadLocal = new ThreadLocal<>();

    public static void set(AuthData val) {
        dataThreadLocal.set(val);
    }

    public static AuthData get() {
        AuthData data = dataThreadLocal.get();
        if (data == null) {
            data = new AuthDataHolder.AuthData();
            log.debug("Init set AuthDataHolder data...");
            dataThreadLocal.set(data);
        }
        return data;
    }

    public static void clear() {
        if (log.isDebugEnabled()) {
            log.debug("Clear AuthDataHolder: " + get());
        }
        dataThreadLocal.remove();
    }


    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class AuthData {
        private String remoteIp;
        private String clientId;
        private Long userId;
        private String accountName;
        private Long tenantId;
        private List<String> roles;

        public String getAuditString() {
            return accountName;
        }

        public Long getTenantId() {
            return tenantId == null ? BaseConstant.DEFAULT_TENANT_ID : tenantId;
        }
    }
}
