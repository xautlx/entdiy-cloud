package com.entdiy.common.dto;

import io.swagger.annotations.ApiModelProperty;

public abstract class BaseTreeEditDto extends BaseEditDto {

    @ApiModelProperty(value = "上级节点主键")
    private Long parentId;
}
