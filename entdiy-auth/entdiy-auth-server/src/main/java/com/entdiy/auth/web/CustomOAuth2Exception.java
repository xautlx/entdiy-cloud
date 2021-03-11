package com.entdiy.auth.web;

import com.entdiy.common.model.ViewResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = CustomOAuth2ExceptionJackson2Serializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {

    public CustomOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomOAuth2Exception(String msg) {
        super(msg);
    }

    public static CustomOAuth2Exception valueOf(ViewResult viewResult) {
        CustomOAuth2Exception ex = new CustomOAuth2Exception(null);
        ex.addAdditionalInformation("code", viewResult.getCode());
        ex.addAdditionalInformation("path", viewResult.getPath());
        ex.addAdditionalInformation("message", viewResult.getMessage());
        if (StringUtils.isNotBlank(viewResult.getTraceId())) {
            ex.addAdditionalInformation("traceId", viewResult.getTraceId());
        }
        ex.addAdditionalInformation("timestamp", String.valueOf(viewResult.getTimestamp()));
        return ex;
    }
}
