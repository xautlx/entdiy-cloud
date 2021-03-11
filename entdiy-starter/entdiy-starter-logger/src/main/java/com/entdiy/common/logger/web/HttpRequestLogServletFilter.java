package com.entdiy.common.logger.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class HttpRequestLogServletFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.trace("Invoking HttpRequestLogServletFilter...");
        boolean debugEnabled = log.isTraceEnabled() || log.isDebugEnabled();
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //提取verbose参数标识是否开启详细信息输出
        boolean verbose = log.isTraceEnabled() || BooleanUtils.toBoolean(request.getParameter("verbose"));
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (debugEnabled) {
            HttpRequestLogProcessor.logRequest(request, verbose);
        }

        filterChain.doFilter(request, servletResponse);

        if (debugEnabled) {
            HttpRequestLogProcessor.logResponse(response, verbose);
        }
    }
}
