package com.entdiy.common.flyway;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty("spring.flyway.enabled-after-jpa")
@Configuration
public class FlywayConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        String flywayTable = "flyway_" + StringUtils.replace(StrUtil.toUnderlineCase(applicationName), "-", "_");

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("flyway-table", flywayTable);

        Flyway.configure()
                .baselineOnMigrate(true)
                .baselineVersion("0.0.0")
                .validateOnMigrate(false)
                .table(flywayTable)
                .placeholders(placeholders)
                .dataSource(dataSource).load().migrate();
    }

}
