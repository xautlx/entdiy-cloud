package com.entdiy.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.entdiy.common.web.json.JsonViews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 * 实体对象基类
 */
@Setter
@Getter
@ExcelIgnoreUnannotated
@MappedSuperclass
@ApiModel("审计实体基类")
public abstract class BaseAuditableEntity extends BaseIdVersionEntity implements LogicDeletePersistable {

    public final static String BOOLEAN_TRUE_COLUMN_DEFINITION = "bit(1) default 1";
    public final static String BOOLEAN_FALSE_COLUMN_DEFINITION = "bit(1) default 1";

    public final static String CREATED_AUDIT_DATA_COLUMN_NAME = "created_audit_data";
    public final static String UPDATED_AUDIT_DATA_COLUMN_NAME = "updated_audit_data";

    @TableLogic
    @TableField(value = LogicDeletePersistable.LOGIC_DELETED_COLUMN_NAME, fill = FieldFill.INSERT, select = false)
    @Column(nullable = true, name = LogicDeletePersistable.LOGIC_DELETED_COLUMN_NAME, columnDefinition = "bit(1) default 0")
    @ApiModelProperty(value = "逻辑删除", notes = "默认创建时赋值0表示未逻辑删除；逻辑删除操作更新null，从而兼容唯一性约束限制", hidden = true)
    @JsonIgnore
    private Byte logicDeleted;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @ApiModelProperty(name = "更新操作时间", hidden = true)
    @JsonView({JsonViews.AdminReadOnly.class})
    private LocalDateTime createdAt;

    @TableField(value = "created_user_id", fill = FieldFill.INSERT)
    @ApiModelProperty(name = "更新操作用户主键", hidden = true)
    @JsonIgnore
    private Long createdUserId;

    @TableField(value = CREATED_AUDIT_DATA_COLUMN_NAME, fill = FieldFill.INSERT, select = false, updateStrategy = FieldStrategy.NEVER)
    @Column(length = 512, nullable = true, name = CREATED_AUDIT_DATA_COLUMN_NAME)
    @ApiModelProperty(value = "创建操作审计信息", notes = "可根据实际需要放字符串或JSON字符串", hidden = true)
    @JsonIgnore
    private String createdAuditData;

    @TableField(value = "updated_at", fill = FieldFill.UPDATE, select = false)
    @ApiModelProperty(name = "更新操作时间", hidden = true)
    @JsonIgnore
    private LocalDateTime updatedAt;


    @TableField(value = "updated_user_id", fill = FieldFill.UPDATE, select = false)
    @ApiModelProperty(name = "更新操作用户主键", hidden = true)
    @JsonIgnore
    private Long updatedUserId;

    @TableField(value = UPDATED_AUDIT_DATA_COLUMN_NAME, fill = FieldFill.UPDATE, select = false)
    @Column(length = 512, nullable = true, name = UPDATED_AUDIT_DATA_COLUMN_NAME)
    @ApiModelProperty(name = "更新操作审计信息", notes = "可根据实际需要放字符串或JSON字符串", hidden = true)
    @JsonIgnore
    private String updatedAuditData;

    @JsonIgnore
    @Transient
    @ApiModelProperty(name = "扩展数据", hidden = true)
    @TableField(exist = false)
    private String extData;

    /**
     * 拷贝克隆创建对象重置相关审计属性
     */
    @JsonIgnore
    @Transient
    public void resetForCopy() {
        this.setId(null);
        this.setVersion(1);
        this.setCreatedAt(null);
        this.setCreatedAuditData(null);
        this.setUpdatedAt(null);
        this.setUpdatedAuditData(null);
    }
}
