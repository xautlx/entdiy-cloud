package com.entdiy.auth.security;

import com.entdiy.auth.web.OAuth2ExceptionTranslator;
import com.entdiy.common.model.ViewResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败转换JSON响应输出
 */
public class JsonBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ViewResult viewResult = OAuth2ExceptionTranslator.buildResponseBody(request, response, authException);
        response.getWriter().write(objectMapper.writeValueAsString(viewResult));
    }
}
