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
import java.time.LocalDateTime;

/**
 * 实体对象基类
 */
@Data
@ExcelIgnoreUnannotated
@MappedSuperclass
public abstract class BaseEntity extends BaseVersionEntity implements LogicDeletePersistable, TenantPersistable<Long> {

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

    @TableField(value = "created_by", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(value = "创建操作用户标识", notes = "如记录OAuth2中username信息", hidden = true)
    @Column(length = 128)
    @JsonIgnore
    private String createdBy;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @ApiModelProperty(name = "更新操作时间", hidden = true)
    private LocalDateTime createdAt;

    @TableField(value = "created_from", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(name = "创建操作来源", notes = "如记录OAuth2中client_id信息", hidden = true)
    @Column(length = 128)
    @JsonIgnore
    private String createdFrom;

    @TableField(value = "created_ip", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(value = "创建操作来源IP", hidden = true)
    @Column(length = 64)
    @JsonIgnore
    private String createdIp;

    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(name = "更新操作用户标识", notes = "如记录OAuth2中username信息", hidden = true)
    @Column(length = 128)
    @JsonIgnore
    private String updatedBy;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(name = "更新操作时间", hidden = true)
    @JsonIgnore
    private LocalDateTime updatedAt;

    @TableField(value = "updated_from", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(name = "更新操作来源", notes = "如记录OAuth2中client_id信息", hidden = true)
    @Column(length = 128)
    @JsonIgnore
    private String updatedFrom;

    @TableField(value = "updated_ip", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(value = "更新操作来源IP", hidden = true)
    @Column(length = 64)
    @JsonIgnore
    private String updatedIp;

    /**
     * 拷贝克隆创建对象重置相关审计属性
     */
    @JsonIgnore
    @Transient
    public void resetForCopy() {
        this.setId(null);
        this.setVersion(1);
        this.setCreatedAt(null);
        this.setCreatedBy(null);
        this.setCreatedFrom(null);
        this.setCreatedIp(null);
        this.setUpdatedAt(null);
        this.setUpdatedBy(null);
        this.setUpdatedFrom(null);
        this.setUpdatedIp(null);
    }
}
