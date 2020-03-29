package com.entdiy.common.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "app.context")
public class AppContextProperties {

    @ApiModelProperty(value = "开发模式", notes = "更宽松的权限控制，更多的日志信息。详见application.properties配置参数定义")
    private boolean devMode;

    @ApiModelProperty(value = "演示模式", notes = "对演示环境进行特殊控制以避免不必要的随意数据修改导致系统混乱")
    private boolean demoMode;

    private String buildVersion;

    private String systemName;

    @PostConstruct
    public void init() {
        AppContextHolder.setAppContextProperties(this);
        log.info("Running at: {}", this);
    }
}
