package com.entdiy.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体对象基类
 */
@Data
@ExcelIgnoreUnannotated
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Persistable<Long> {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(value = "禁用标识", notes = "禁用项目用户端不显示")
    private Boolean disabled = Boolean.FALSE;

    @TableField(value = "tenant_id", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NEVER)
    @ApiModelProperty(value = "租户主键", hidden = true)
    @JsonIgnore
    private Long tenantId;

    /**
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    @Column(nullable = false)
    @ApiModelProperty("乐观锁版本")
    private Integer version;

    /**
     * 逻辑删除
     * 默认创建时赋值0表示未逻辑删除；逻辑删除操作更新null，从而兼容唯一性约束限制
     */
    @TableLogic
    @TableField(value = "deleted", fill = FieldFill.INSERT, select = false)
    @Column(nullable = true)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Byte deleted;

    @TableField(value = "created_by", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(hidden = true)
    @Column(length = 128)
    @JsonIgnore
    private String createdBy;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @TableField(value = "created_from", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(hidden = true)
    @Column(length = 128)
    @JsonIgnore
    private String createdFrom;

    @TableField(value = "created_ip", fill = FieldFill.INSERT, select = false)
    @ApiModelProperty(hidden = true)
    @Column(length = 64)
    @JsonIgnore
    private String createdIp;

    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(hidden = true)
    @Column(length = 128)
    @JsonIgnore
    private String updatedBy;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private LocalDateTime updatedAt;

    @TableField(value = "updated_from", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(hidden = true)
    @Column(length = 128)
    @JsonIgnore
    private String updatedFrom;

    @TableField(value = "updated_ip", fill = FieldFill.INSERT_UPDATE, select = false)
    @ApiModelProperty(hidden = true)
    @Column(length = 64)
    @JsonIgnore
    private String updatedIp;

    @Override
    @javax.persistence.Transient
    @JsonIgnore
    public boolean isNew() {
        Serializable id = getId();
        return id == null || StringUtils.isBlank(String.valueOf(id));
    }

    /*
     * 用于快速判断对象是否编辑状态
     */
    @javax.persistence.Transient
    @JsonIgnore
    public boolean isNotNew() {
        return !isNew();
    }

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
