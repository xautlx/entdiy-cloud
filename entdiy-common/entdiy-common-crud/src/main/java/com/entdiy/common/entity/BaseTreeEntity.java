package com.entdiy.common.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.entdiy.common.web.json.JsonViews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Index;

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
public abstract class BaseTreeEntity extends BaseEntity implements TreePersistable<Long> {

    @Column(name = PARENT_ID_COLUMN_NAME, nullable = true)
    @ApiModelProperty(value = "上级节点主键")
    @TableField(value = PARENT_ID_COLUMN_NAME)
    @JsonView({JsonViews.ReadWrite.class})
    private Long parentId;

    @Column(name = PARENT_ID_PATH_COLUMN_NAME, nullable = true, length = 512)
    @ApiModelProperty(value = "上级节点ID路径", notes = "添加索引用于前缀匹配高效查询所有下属层级子节点")
    @TableField(value = PARENT_ID_PATH_COLUMN_NAME)
    @JsonIgnore
    @Index(name = "ix_" + PARENT_ID_PATH_COLUMN_NAME)
    private String parentIdPath = "";

    @Column(name = LEAF_NODE_COLUMN_NAME, nullable = false)
    @ApiModelProperty(value = "是否叶子节点", notes = "便于树形展示显示提示图标")
    @TableField(value = LEAF_NODE_COLUMN_NAME)
    @JsonView({JsonViews.ReadOnly.class})
    private Boolean leafNode = Boolean.TRUE;

    @Transient
    @JsonView({JsonViews.AdminReadOnly.class})
    public String getCurrentIdPath() {
        return getParentIdPath() + "/" + getId();
    }
}
