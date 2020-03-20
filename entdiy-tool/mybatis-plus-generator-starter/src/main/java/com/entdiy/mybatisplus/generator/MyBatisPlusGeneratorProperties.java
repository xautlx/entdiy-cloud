package com.entdiy.mybatisplus.generator;

import com.baomidou.mybatisplus.generator.config.strategy.SuperClassStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mybatis-plus.generator")
public class MyBatisPlusGeneratorProperties {

    private String author;

    private String parentPackageName;

    private String moduleName;

    private String schemaName;

    private String tablePrefix;

    private Class<? extends SuperClassStrategy> superClassStrategy;
}
