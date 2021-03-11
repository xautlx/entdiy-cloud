package com.entdiy.common.logger.web;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 对于提取请求内容的Request进行二次封装以便后续使用
 */
public class HttpServletRequestAdapter extends HttpServletRequestWrapper {
    private InputStream inputStream;

    public HttpServletRequestAdapter(HttpServletRequest request, byte[] payload) {
        super(request);
        inputStream = new ByteArrayInputStream(payload);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }
}