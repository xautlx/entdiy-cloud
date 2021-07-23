package com.entdiy.auth.service;

import com.entdiy.common.core.constant.Constants;
import com.entdiy.common.core.constant.UserConstants;
import com.entdiy.common.core.domain.R;
import com.entdiy.common.core.enums.UserStatus;
import com.entdiy.common.core.exception.BaseException;
import com.entdiy.common.core.exception.CustomException;
import com.entdiy.common.core.utils.StringUtils;
import com.entdiy.common.security.model.LoginUser;
import com.entdiy.common.security.utils.SecurityUtils;
import com.entdiy.system.api.RemoteLogService;
import com.entdiy.system.api.RemoteUserService;
import com.entdiy.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录校验方法
 */
@Component
public class SysLoginService {
    @Autowired
    private RemoteLogService remoteLogService;

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 登录
     */
    public LoginUser login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            throw new CustomException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new CustomException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new CustomException("用户名不在指定范围");
        }
        // 查询用户信息
        R<LoginUser> userResult = remoteUserService.getUserInfo(username);

        if (R.FAIL == userResult.getCode()) {
            throw new BaseException(userResult.getMsg());
        }

        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            throw new CustomException("用户登录信息不正确");
        }
        LoginUser userInfo = userResult.getData();
        SysUser user = remoteUserService.getSysUser(username).getData();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            throw new CustomException("对不起，您的账号状态异常");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            throw new CustomException("对不起，您的账号已停用");
        }
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            //TODO 密码错误次数检测控制
            //remoteLogService.saveLogininfor(username, Constants.LOGIN_FAIL, "用户密码错误");
            throw new CustomException("用户不存在或密码错误");
        }
        remoteLogService.saveLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    public void logout(String loginName) {
        remoteLogService.saveLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }
}
