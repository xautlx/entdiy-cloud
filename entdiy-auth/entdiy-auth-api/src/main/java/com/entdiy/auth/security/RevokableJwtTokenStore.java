package com.entdiy.auth.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 在JWT标准有效期校验的基础上，基于Redis追加黑名单收回控制的TokenStore
 */
@Slf4j
public class RevokableJwtTokenStore extends JwtTokenStore {



    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the DefaultTokenServices if used).
     *
     * @param jwtTokenEnhancer
     */
    public RevokableJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }
}
