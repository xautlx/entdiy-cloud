package com.entdiy.system.api.factory;

import com.entdiy.common.core.domain.R;
import com.entdiy.common.security.model.LoginUser;
import com.entdiy.system.api.RemoteUserService;
import com.entdiy.system.api.domain.SysUser;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 *
 *
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable);
        return new RemoteUserService()
        {
            @Override
            public R<LoginUser> getUserInfo(String username)
            {
                return R.fail("获取登录用户失败:" + throwable.getMessage());
            }

            @Override
            public R<SysUser> getSysUser(String username) {
                return R.fail("获取系统用户失败:" + throwable.getMessage());
            }
        };
    }
}
