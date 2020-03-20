package com.entdiy.jpa;

import com.entdiy.common.entity.BaseTreeEntity;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.Metadata;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.*;
import org.hibernate.tool.schema.internal.StandardTableExporter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Iterator;

public class Swagger2StandardTableExporter extends StandardTableExporter {

    public Swagger2StandardTableExporter(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String[] getSqlCreateStrings(Table table, Metadata metadata) {
        //调整字段顺序把业务字段靠前DDL定义
        java.util.List<Column> lowColumns = Lists.newArrayList();

        //提取Swagger注释追加到DDL脚本
        Iterator<PersistentClass> persistentClasses = metadata.getEntityBindings().iterator();
        while (persistentClasses.hasNext()) {
            PersistentClass persistentClass = persistentClasses.next();
            if (persistentClass.getTable().equals(table)) {
                //表注释
                if (StringUtils.isEmpty(table.getComment())) {
                    ApiModel apiModel = (ApiModel) persistentClass.getMappedClass().getAnnotation(ApiModel.class);
                    if (apiModel != null) {
                        table.setComment(apiModel.value());
                    }
                }
                //字段注释
                Iterator propertyIterator = persistentClass.getPropertyIterator();
                while (propertyIterator.hasNext()) {
                    Property property = (Property) propertyIterator.next();
                    String propertyName = property.getName();
                    SimpleValue simpleValue = (SimpleValue) property.getValue();
                    Column column = (Column) simpleValue.getColumnIterator().next();
                    if (StringUtils.isNotEmpty(column.getComment())) {
                        continue;
                    }

                    //保留id在第一位，其余基类属性记录下来，移动到DDL位置后面
                    if (!"id".equals(propertyName)) {
                        Field superField = ReflectionUtils.findField(BaseTreeEntity.class, propertyName);
                        if (superField != null) {
                            lowColumns.add(column);
                        }
                    }

                    Field field = ReflectionUtils.findField(persistentClass.getMappedClass(), propertyName);
                    ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                    if (apiModelProperty != null && !apiModelProperty.hidden()) {
                        column.setComment(apiModelProperty.value());
                        if (StringUtils.isEmpty(column.getComment())) {
                            column.setComment(apiModelProperty.name());
                        }
                    }
                }
            }
        }

        //调整字段顺序把业务字段靠前DDL定义
        Iterator columnIterator = table.getColumnIterator();
        while (columnIterator.hasNext()) {
            Column column = (Column) columnIterator.next();
            if (CollectionUtils.contains(lowColumns.iterator(), column)) {
                columnIterator.remove();
            }
        }
        lowColumns.forEach(one -> table.addColumn(one));
        return super.getSqlCreateStrings(table, metadata);
    }

}
