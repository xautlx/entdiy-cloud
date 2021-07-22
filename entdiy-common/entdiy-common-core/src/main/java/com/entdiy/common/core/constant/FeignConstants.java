package com.entdiy.common.core.constant;


public class FeignConstants
{
    /**
     * 系统模块的service url
     * （主要用于dev本地开发绕开nacos服务发现而是指定url定向调用http地址服务）
     */
    public static final String SERVICE_URL = "${app.feign.url.system:}";
}
