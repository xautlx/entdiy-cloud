package com.entdiy.auth.config;

import com.entdiy.auth.security.BlackListRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@Order(50)
public class RedisTokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore tokenStore() {
        BlackListRedisTokenStore redisTokenStore = new BlackListRedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("oauth2:");
        return redisTokenStore;
    }

}
