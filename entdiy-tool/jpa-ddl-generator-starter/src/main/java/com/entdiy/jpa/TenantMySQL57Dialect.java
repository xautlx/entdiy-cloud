package com.entdiy.jpa;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.unique.UniqueDelegate;
import org.hibernate.mapping.Table;
import org.hibernate.tool.schema.internal.StandardTableExporter;
import org.hibernate.tool.schema.spi.Exporter;

/**
 * 1，基于租户和逻辑删除定制DDL唯一约束生成逻辑
 * 2，提取Swagger注解追加到DDL注释语句
 */
public class TenantMySQL57Dialect extends MySQL57Dialect {

    private UniqueDelegate uniqueDelegate = new TenantMySQLUniqueDelegate(this);

    private StandardTableExporter tableExporter = new Swagger2StandardTableExporter(this);

    @Override
    public Exporter<Table> getTableExporter() {
        return tableExporter;
    }

    @Override
    public UniqueDelegate getUniqueDelegate() {
        return uniqueDelegate;
    }
}
