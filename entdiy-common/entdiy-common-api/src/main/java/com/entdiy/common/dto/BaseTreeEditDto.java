package com.entdiy.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseTreeEditDto extends BaseEditDto {

    @Getter
    @Setter
    @ApiModelProperty(value = "上级节点主键")
    private Long parentId;
}
