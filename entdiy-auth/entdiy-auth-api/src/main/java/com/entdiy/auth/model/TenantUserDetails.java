package com.entdiy.auth.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TenantUserDetails extends User {

    private Long tenantId;
    private String tenantCode;
    private Long userId;

    public TenantUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
    }
}
