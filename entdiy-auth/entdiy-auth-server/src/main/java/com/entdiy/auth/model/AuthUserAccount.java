package com.entdiy.auth.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class AuthUserAccount {
    private String tenantId;
    private String tenantCode;

    private String password;
    private String username;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
