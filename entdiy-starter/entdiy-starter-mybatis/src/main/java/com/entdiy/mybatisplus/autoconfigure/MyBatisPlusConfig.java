package com.entdiy.mybatisplus.autoconfigure;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.entdiy.mybatisplus.plugins.LogicDeleteSqlInjector;
import com.entdiy.mybatisplus.plugins.MybatisMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.entdiy.**.mapper")
public class MyBatisPlusConfig {

    @Bean
    public MybatisMetaObjectHandler mybatisMetaObjectHandler() {
        return new MybatisMetaObjectHandler();
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public LogicDeleteSqlInjector logicDeleteSqlInjector() {
        return new LogicDeleteSqlInjector();
    }
}
