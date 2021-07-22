package com.entdiy.common.feign;

import com.entdiy.common.core.constant.CacheConstants;
import com.entdiy.common.core.utils.ServletUtils;
import com.entdiy.common.core.utils.StringUtils;
import com.entdiy.common.core.utils.ip.IpUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * feign 请求拦截器
 *
 * 
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (StringUtils.isNotNull(httpServletRequest)) {
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
            // 传递用户信息请求头，防止丢失
            String userId = headers.get(CacheConstants.DETAILS_USER_ID);
            if (StringUtils.isNotEmpty(userId)) {
                requestTemplate.header(CacheConstants.DETAILS_USER_ID, userId);
            }
            String userName = headers.get(CacheConstants.DETAILS_USERNAME);
            if (StringUtils.isNotEmpty(userName)) {
                requestTemplate.header(CacheConstants.DETAILS_USERNAME, userName);
            }
            String authentication = headers.get(CacheConstants.AUTHORIZATION_HEADER);
            if (StringUtils.isNotEmpty(authentication)) {
                requestTemplate.header(CacheConstants.AUTHORIZATION_HEADER, authentication);
            }

            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtils.getIpAddr(ServletUtils.getRequest()));
        }
    }
}
