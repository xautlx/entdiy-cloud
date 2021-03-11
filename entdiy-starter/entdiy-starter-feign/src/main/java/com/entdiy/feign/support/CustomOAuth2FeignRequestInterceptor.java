package com.entdiy.feign.support;

import cn.hutool.core.codec.Base64;
import com.entdiy.common.web.ApplicationContextHolder;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CustomOAuth2FeignRequestInterceptor extends OAuth2FeignRequestInterceptor {

    public CustomOAuth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource) {
        super(oAuth2ClientContext, resource);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (log.isInfoEnabled()) {
            log.info("FeignRequest: {}", requestTemplate);
        }
        String authorization = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //在定时任务或消息接口触发时，没有Request
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            authorization = request.getHeader(OAuth2FeignRequestInterceptor.AUTHORIZATION);
        }
        //优先从源头请求获取token传递，如果没有则自动发起token请求后追加到后续请求
        if (StringUtils.isNotBlank(authorization)) {
            requestTemplate.header(OAuth2FeignRequestInterceptor.AUTHORIZATION, authorization);
        } else {
            if (ApplicationContextHolder.isDevMode()) {
                String basic = "Basic " + Base64.decode("DEV:123456");
                log.debug("Add header at DEV mode {}:{}", OAuth2FeignRequestInterceptor.AUTHORIZATION, basic);
                requestTemplate.header(OAuth2FeignRequestInterceptor.AUTHORIZATION, basic);
            } else {
                super.apply(requestTemplate);
            }

        }
    }
}
