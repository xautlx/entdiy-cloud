package com.entdiy.common.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@Slf4j
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "app")
public class AppConfigProperties {

    @ApiModelProperty(value = "开发模式", notes = "针对开发运行做一些特殊处理提高开发效率")
    private boolean devMode;

    @ApiModelProperty(value = "演示模式", notes = "对演示环境进行特殊控制以避免不必要的随意数据修改导致系统混乱")
    private boolean demoMode;

    private String buildVersion;

    private String systemName;

    @ApiModelProperty(value = "业务组件扫描包", notes = "可逗号分隔")
    private String scanPackages;

    private Security security = new Security();

    @PostConstruct
    public void init() {
        log.info("Running at: {}", this);
    }

    @Setter
    @Getter
    @ToString(exclude = "jwtSigningKey")
    public static class Security {
        private String jwtSigningKey = "signingKey123456@Entdiy";
    }
}
