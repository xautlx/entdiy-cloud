package com.entdiy.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.entdiy.common.web.json.JsonViews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 实体对象基类
 */
@Setter
@Getter
@ExcelIgnoreUnannotated
@MappedSuperclass
public abstract class BaseIdVersionEntity extends ActiveRecordModel implements Serializable, Persistable<Long> {


    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    @JsonView({JsonViews.Public.class})
    private Long id;

    /**
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    @Column(nullable = false, columnDefinition = "int default 1")
    @ApiModelProperty(value = "乐观锁版本")
    @JsonView({JsonViews.Public.class})
    private Integer version;

    @Override
    @javax.persistence.Transient
    @JsonIgnore
    @ApiModelProperty(hidden = true)
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
    @ApiModelProperty(hidden = true)
    public boolean isNotNew() {
        return !isNew();
    }
}
