package com.entdiy.mybatisplus.autoconfigure;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.entdiy.common.auth.AuthDataHolder;
import com.entdiy.common.constant.BaseConstant;
import com.entdiy.common.entity.TenantPersistable;
import com.entdiy.common.mapper.LogicDeleteSqlInjector;
import com.entdiy.common.mapper.MybatisMetaObjectHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
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
                Long tenantId = AuthDataHolder.get().getTenantId();
                if (tenantId == null) {
                    tenantId = BaseConstant.DEFAULT_TENANT_ID;
                }
                return new LongValue(tenantId);
            }

            @Override
            public String getTenantIdColumn() {
                return TenantPersistable.TENANT_ID_COLUMN_NAME;
            }

            @Override
            public boolean doTableFilter(String tableName) {
                if ("sys_tenant".equals(tableName)) {
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
