package com.entdiy.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.entdiy.common.web.json.JsonViews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 实体对象基类
 */
@Setter
@Getter
@ExcelIgnoreUnannotated
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEntity extends BaseAuditableEntity implements TenantPersistable<Long> {

    @TableField(value = TenantPersistable.TENANT_ID_COLUMN_NAME, insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NEVER)
    @Column(nullable = false, name = TenantPersistable.TENANT_ID_COLUMN_NAME, updatable = false)
    @ApiModelProperty(value = "租户主键")
    @JsonIgnore
    private Long tenantId;

    @Transient
    @JsonProperty
    @ApiModelProperty(hidden = true)
    @JsonView({JsonViews.AdminReadOnly.class})
    public String getDisplay() {
        return "[" + getId() + "]" + this.getClass().getSimpleName();
    }
}
