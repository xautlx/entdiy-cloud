package com.entdiy.common.security.service;


import com.entdiy.common.security.model.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * token验证处理
 *
 *
 */
public interface TokenService {

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUser loginUser) ;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser() ;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request);

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser);

    public void delLoginUser(String token);

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser);

}
