package com.entdiy.common.logger.web;

import com.entdiy.common.util.HttpUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpRequestLogProcessor {

    public static byte[] logRequest(HttpServletRequest request, boolean verbose) {
        String uri = request.getRequestURI();
        log.trace("Http Request data for: {}", uri);
        byte[] _payload = null;

        //静态资源直接跳过
        if (uri == null || uri.startsWith("/druid") || uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".gif") || uri.endsWith(".png") || uri.endsWith(".jpg")
                || uri.endsWith(".woff") || uri.endsWith(".woff2") || uri.endsWith(".ico") || uri.endsWith(".mp3")) {
            log.trace("Log skipped as static resource");
            return null;
        }

        //忽略日志记录
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            return null;
        }

        Map<String, String> dataMap = buildRequestInfoDataMap(request, verbose);
        StringBuilder sb = new StringBuilder("HTTP Request Info:");
        for (Map.Entry<String, String> me : dataMap.entrySet()) {
            sb.append(StringUtils.rightPad("\n - " + me.getKey(), 35) + ": " + me.getValue());
        }

        if (verbose) {
            if (!isMultipart(request) && !isBinaryContent(request)) {
                try {
                    String charEncoding = request.getCharacterEncoding() != null
                            ? request.getCharacterEncoding() :
                            "UTF-8";
                    _payload = IOUtils.toByteArray(request.getInputStream());

                    sb.append(StringUtils.rightPad("\n - Request Body", 35) + ": " + new String(_payload, charEncoding));

                } catch (UnsupportedEncodingException e) {
                    log.warn("UnsupportedEncodingException", e);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        log.debug(sb.toString());
        return _payload;
    }

    public static void logResponse(HttpServletResponse response, boolean verbose) {
        Map<String, String> headers = new HashMap<>(16);
        response.getHeaderNames().forEach(s -> headers.put(s, response.getHeader(s)));

        StringBuilder sb = new StringBuilder("HTTP Response Status: " + response.getStatus());
        if (verbose) {
            for (Map.Entry<String, String> me : headers.entrySet()) {
                sb.append(StringUtils.rightPad("\n - Header[" + me.getKey(), 35) + "]: " + me.getValue());
            }
        }
        log.debug(sb.toString());
    }

    private static boolean isBinaryContent(final HttpServletRequest request) {
        return request.getContentType() != null && (request.getContentType().startsWith("image") || request.getContentType().startsWith("video") || request.getContentType().startsWith("audio"));
    }

    private static boolean isMultipart(final HttpServletRequest request) {
        return request.getContentType() != null && (request.getContentType().startsWith("multipart/form-data") || request.getContentType().startsWith("application/octet-stream"));
    }

    private static Map<String, String> buildRequestInfoDataMap(HttpServletRequest request, boolean verbose) {
        Map<String, String> dataMap = Maps.newLinkedHashMap();
        // Request相关的参数、属性等数据组装
        dataMap.put("Method", request.getMethod());
        dataMap.put("RequestURL", request.getRequestURL().toString());
        if (verbose) {
            dataMap.put("RequestURI", request.getRequestURI());
            dataMap.put("QueryString", request.getQueryString());
            dataMap.put("RemoteHost", HttpUtil.getIpAddress(request));
            dataMap.put("ContextPath", request.getContextPath());
            dataMap.put("RemoteHost", request.getRemoteHost());
            dataMap.put("RemotePort", String.valueOf(request.getRemotePort()));
            dataMap.put("RemoteUser", request.getRemoteUser());
            dataMap.put("LocalAddr", request.getLocalAddr());
            dataMap.put("LocalName", request.getLocalName());
            dataMap.put("LocalPort", String.valueOf(request.getLocalPort()));
            dataMap.put("ServerName", request.getServerName());
            dataMap.put("ServerPort", String.valueOf(request.getServerPort()));

        }

        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = StringUtils.join(request.getParameterValues(paramName), ",");
            if (paramValue != null && paramValue.length() > 100) {
                paramValue = paramValue.substring(0, 100) + "...";
            }
            if ("password".equals(paramName) || paramName.startsWith("password") || paramName.endsWith("password")) {
                paramValue = "[PROTECTED]";
            }
            dataMap.put("Parameter[" + paramName + "]", paramValue);
        }

        Enumeration<?> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            if ("Authorization".equalsIgnoreCase(headerName)) {
                dataMap.put("Header[" + headerName + "]", headerValue);
            } else if ("Content-Type".equalsIgnoreCase(headerName)) {
                dataMap.put("Header[" + headerName + "]", headerValue);
            } else if (verbose) {
                dataMap.put("Header[" + headerName + "]", headerValue);
            }
        }

        if (request instanceof MultipartHttpServletRequest) {
            // 转型为MultipartHttpRequest
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 获得上传的文件（根据前台的name名称得到上传的文件）
            MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
            for (String key : multiValueMap.keySet()) {
                dataMap.put("Multipart[" + key + "]", multiValueMap.getFirst(key).getName());
            }
        }

        if (verbose) {
            Enumeration<?> attrNames = request.getAttributeNames();
            while (attrNames.hasMoreElements()) {
                String attrName = (String) attrNames.nextElement();
                Object attrValue = request.getAttribute(attrName);
                if (attrValue == null) {
                    attrValue = "NULL";
                }
                String attr = attrValue.toString();
                if (attr != null && attr.toString().length() > 100) {
                    attr = attr.substring(0, 100) + "...";
                }
                dataMap.put("Attribute[" + attrName + "]", attr);
            }

            HttpSession session = request.getSession(false);
            if (session != null) {
                Enumeration<?> sessionAttrNames = session.getAttributeNames();
                while (sessionAttrNames.hasMoreElements()) {
                    String attrName = (String) sessionAttrNames.nextElement();
                    Object attrValue = session.getAttribute(attrName);
                    if (attrValue == null) {
                        attrValue = "NULL";
                    }
                    String attr = attrValue.toString();
                    if (attr != null && attr.toString().length() > 100) {
                        attr = attr.toString().substring(0, 100) + "...";
                    }
                    dataMap.put("Session[" + attrName + "]", attr);
                }
            }
        }
        return dataMap;
    }

}
