package com.entdiy.auth.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 基于黑名单机制移除JWT Token，以支持踢出登录等个性化需求
 */
@Slf4j
public class BlackListRedisTokenStore extends RedisTokenStore {

    private static final String ACCESS_BLACKLIST = "access_blacklist:";

    private final RedisConnectionFactory connectionFactory;

    private String prefix = "";

    private Method redisConnectionSet_2_0;

    private static final boolean springDataRedis_2_0 = ClassUtils.isPresent(
            "org.springframework.data.redis.connection.RedisStandaloneConfiguration",
            RedisTokenStore.class.getClassLoader());

    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    public BlackListRedisTokenStore(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
        this.connectionFactory = connectionFactory;
        if (springDataRedis_2_0) {
            this.loadRedisConnectionMethods_2_0();
        }
    }

    private void loadRedisConnectionMethods_2_0() {
        this.redisConnectionSet_2_0 = ReflectionUtils.findMethod(
                RedisConnection.class, "set", byte[].class, byte[].class);
    }

    private byte[] serialize(Object object) {
        return serializationStrategy.serialize(object);
    }

    private byte[] serializeKey(String object) {
        return serialize(prefix + object);
    }

    public void setPrefix(String prefix) {
        super.setPrefix(prefix);
        this.prefix = prefix;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        byte[] serializedAccessToken = serialize(token);
        byte[] accessBlacklistKey = serializeKey(ACCESS_BLACKLIST + token.getValue());

        //将删除的token移动到黑名单集合
        RedisConnection conn = connectionFactory.getConnection();
        try {
            conn.openPipeline();
            if (springDataRedis_2_0) {
                try {
                    this.redisConnectionSet_2_0.invoke(conn, accessBlacklistKey, serializedAccessToken);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                conn.set(accessBlacklistKey, serializedAccessToken);
            }
            if (token.getExpiration() != null) {
                int seconds = token.getExpiresIn();
                conn.expire(accessBlacklistKey, seconds);
            }
            conn.closePipeline();
        } finally {
            conn.close();
        }

        //执行当前token删除
        removeAccessToken(token.getValue());
    }
}