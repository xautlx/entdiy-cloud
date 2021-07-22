package com.entdiy.common.redis.service;

import com.entdiy.common.core.constant.CacheConstants;
import com.entdiy.common.core.utils.IdUtils;
import com.entdiy.common.security.utils.SecurityUtils;
import com.entdiy.common.core.utils.ServletUtils;
import com.entdiy.common.core.utils.StringUtils;
import com.entdiy.common.core.utils.ip.IpUtils;
import com.entdiy.common.security.model.LoginUser;
import com.entdiy.common.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * 
 */
public class RedisTokenService implements TokenService {

    @Autowired
    private RedisService redisService;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUser loginUser) {
        // 生成token
        if (StringUtils.isEmpty(loginUser.getToken())) {
            //自有创建Token追加前缀避免与外部Token算法重叠
            String token = "000" + IdUtils.fastUUID();
            loginUser.setToken(token);
        }
        loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        refreshToken(loginUser);

        // 保存或更新用户token
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", loginUser.getToken());
        map.put("expires_in", loginUser.getExpireIn());
        redisService.setCacheObject(ACCESS_TOKEN + loginUser.getToken(), loginUser, loginUser.getExpireIn(), TimeUnit.SECONDS);
        return map;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            LoginUser user = redisService.getCacheObject(userKey);
            return user;
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisService.deleteObject(userKey);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, loginUser.getExpireIn(), TimeUnit.SECONDS);
    }

    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }
}
