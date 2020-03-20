package com.entdiy.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

public class BaseTreeListDto extends BaseListDto {

    @ApiModelProperty(value = "上级节点主键")
    @TableField(value = "parent_id")
    private Long parentId;

    @ApiModelProperty(value = "Nested Set Tree模型Left值")
    @TableField(value = "lft")
    private Integer lft = 0;

    @ApiModelProperty(value = "Nested Set Tree模型Right值")
    @TableField(value = "rgt")
    private Integer rgt = 0;

    @ApiModelProperty(value = "节点层级")
    @TableField(value = "depth")
    private Integer depth = 0;
}
