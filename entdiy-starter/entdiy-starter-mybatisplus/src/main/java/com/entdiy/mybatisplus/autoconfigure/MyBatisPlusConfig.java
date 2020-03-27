package com.entdiy.mybatisplus.autoconfigure;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.entdiy.common.auth.AuthDataHolder;
import com.entdiy.common.entity.TenantPersistable;
import com.entdiy.common.mapper.LogicDeleteSqlInjector;
import com.entdiy.common.mapper.MybatisMetaObjectHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public MybatisMetaObjectHandler mybatisMetaObjectHandler() {
        return new MybatisMetaObjectHandler();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();

        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId(boolean where) {
                String tenantId = AuthDataHolder.get().getTenantId();
                return StringUtils.isBlank(tenantId) ? null : new StringValue(tenantId);
            }

            @Override
            public String getTenantIdColumn() {
                return TenantPersistable.TENANT_ID_COLUMN_NAME;
            }

            @Override
            public boolean doTableFilter(String tableName) {
                //用户的tenantId为空（超级管理员）则跳过租户条件
                String tenantId = AuthDataHolder.get().getTenantId();
                if (StringUtils.isBlank(tenantId)) {
                    return true;
                }
                return false;
            }
        });
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
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