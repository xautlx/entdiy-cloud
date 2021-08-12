package com.entdiy.system.api.constant;

/**
 * FeignClient服务属性常量定义
 */
public class FileFeignConstants {

    /**
     * 模块的service id属性
     */
    public static final String SERVICE_ID = "entdiy-file";

    /**
     * 模块的service url属性,用于开发模式可定制设置为请求本地或特定url的服务
     */
    public static final String SERVICE_URL = "${app.feign.url.file:}";
}
