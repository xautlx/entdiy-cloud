package com.entdiy.system.api;

import com.entdiy.common.core.domain.R;
import com.entdiy.common.security.model.LoginUser;
import com.entdiy.system.api.constant.SystemFeignConstants;
import com.entdiy.system.api.domain.SysUser;
import com.entdiy.system.api.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户服务
 */
@FeignClient(contextId = "remoteUserService",
        value = SystemFeignConstants.SERVICE_ID,
        url = SystemFeignConstants.SERVICE_URL,
        fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    @GetMapping(value = "/user/info/{username}")
    R<LoginUser> getUserInfo(@PathVariable("username") String username);

    @GetMapping(value = "/user/sysUser/{username}")
    R<SysUser> getSysUser(@PathVariable("username") String username);
}
