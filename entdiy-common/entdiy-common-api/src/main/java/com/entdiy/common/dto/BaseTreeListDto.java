package com.entdiy.common.dto;

import io.swagger.annotations.ApiModelProperty;

public class BaseTreeListDto extends BaseListDto {

    @ApiModelProperty(value = "上级节点主键")
    private Long parentId;

    @ApiModelProperty(value = "Nested Set Tree模型Left值")
    private Integer lft = 0;

    @ApiModelProperty(value = "Nested Set Tree模型Right值")
    private Integer rgt = 0;

    @ApiModelProperty(value = "节点层级")
    private Integer depth = 0;
}
