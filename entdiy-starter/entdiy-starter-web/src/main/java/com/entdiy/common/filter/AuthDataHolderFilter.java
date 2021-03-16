package com.entdiy.common.filter;

import com.entdiy.common.auth.AuthDataHolder;
import com.entdiy.common.util.HttpUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthDataHolderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        AuthDataHolder.AuthData authData = AuthDataHolder.get();
        SecurityContext securityContext=SecurityContextHolder.getContext();
        if(securityContext!=null){
            Authentication authentication= securityContext.getAuthentication();
        }
        authData.setRemoteIp(HttpUtil.getIpAddress((HttpServletRequest)servletRequest));

        filterChain.doFilter(servletRequest,servletResponse);

        //清理ThreadLocal变量避免内存泄露
        AuthDataHolder.clear();
    }
}
