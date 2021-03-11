package com.entdiy.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseTreeListDto extends BaseListDto {

    @ApiModelProperty(value = "上级节点主键")
    private Long parentId;
    
    @ApiModelProperty(value = "上级节点ID路径")
    private String parentIdPath;

    @ApiModelProperty(value = "是否叶子节点", notes = "便于树形展示显示提示图标")
    private Boolean leafNode = Boolean.TRUE;
}
