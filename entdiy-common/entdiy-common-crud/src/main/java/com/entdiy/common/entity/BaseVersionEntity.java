package com.entdiy.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 实体对象基类
 */
@Data
@ExcelIgnoreUnannotated
@MappedSuperclass
public abstract class BaseVersionEntity implements Serializable, Persistable<Long> {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    @Column(nullable = false)
    @ApiModelProperty(value = "乐观锁版本", hidden = true)
    private Integer version;

    @Column(nullable = false)
    @ApiModelProperty(value = "禁用标识", notes = "禁用项目用户端不显示")
    private Boolean disabled = Boolean.FALSE;

    @Override
    @javax.persistence.Transient
    @JsonIgnore
    public boolean isNew() {
        Long id = getId();
        if (id == null || id <= 0) {
            return true;
        }
        return false;
    }

    /*
     * 用于快速判断对象是否编辑状态
     */
    @javax.persistence.Transient
    @JsonIgnore
    public boolean isNotNew() {
        return !isNew();
    }
}
