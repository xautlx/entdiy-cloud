package com.entdiy.common.logger.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class HttpRequestLogMvcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.trace("Invoking HttpRequestLogMvcFilter...");
        boolean debugEnabled = log.isTraceEnabled() || log.isDebugEnabled();
        //提取verbose参数标识是否开启详细信息输出
        boolean verbose = log.isTraceEnabled() || BooleanUtils.toBoolean(request.getParameter("verbose"));

        if (debugEnabled) {
            byte[] payload = HttpRequestLogProcessor.logRequest(request, verbose);
            if (payload != null) {
                request = new HttpServletRequestAdapter(request, payload);
            }
        }

        chain.doFilter(request, response);

        if (debugEnabled) {
            HttpRequestLogProcessor.logResponse(response, verbose);
        }
    }
}
