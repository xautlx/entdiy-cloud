package com.entdiy.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.beans.Transient;

/**
 * 实体对象基类
 */
@Data
@ExcelIgnoreUnannotated
@MappedSuperclass
public abstract class BaseEntity extends BaseVersionEntity implements LogicDeletePersistable, TenantPersistable<Long> {

    public final static String CREATED_AUDIT_DATA_COLUMN_NAME = "created_audit_data";
    public final static String UPDATED_AUDIT_DATA_COLUMN_NAME = "updated_audit_data";

    @TableField(value = TenantPersistable.TENANT_ID_COLUMN_NAME, insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NEVER)
    @Column(nullable = true, name = TenantPersistable.TENANT_ID_COLUMN_NAME, updatable = false)
    @ApiModelProperty(value = "租户主键", hidden = true)
    @JsonIgnore
    private Long tenantId;

    @TableLogic
    @TableField(value = LogicDeletePersistable.LOGIC_DELETED_COLUMN_NAME, fill = FieldFill.INSERT, select = false)
    @Column(nullable = true, name = LogicDeletePersistable.LOGIC_DELETED_COLUMN_NAME)
    @ApiModelProperty(value = "逻辑删除", notes = "默认创建时赋值0表示未逻辑删除；逻辑删除操作更新null，从而兼容唯一性约束限制", hidden = true)
    @JsonIgnore
    private Byte logicDeleted;

    @TableField(value = CREATED_AUDIT_DATA_COLUMN_NAME, fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(value = "创建操作审计信息", notes = "JSON字符串", hidden = true)
    @Column(length = 512)
    @JsonIgnore
    private String createdAuditData;

    @TableField(value = UPDATED_AUDIT_DATA_COLUMN_NAME, fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(name = "更新操作审计信息", notes = "JSON字符串", hidden = true)
    @Column(length = 512)
    @JsonIgnore
    private String updatedAuditData;

    /**
     * 拷贝克隆创建对象重置相关审计属性
     */
    @JsonIgnore
    @Transient
    public void resetForCopy() {
        this.setId(null);
        this.setVersion(1);
        this.setCreatedAuditData(null);
        this.setUpdatedAuditData(null);
    }
}
