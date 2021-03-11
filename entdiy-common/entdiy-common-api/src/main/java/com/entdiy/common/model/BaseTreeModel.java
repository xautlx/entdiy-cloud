package com.entdiy.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseTreeModel extends BaseModel{

    @Getter
    @Setter
    @ApiModelProperty(value = "上级节点主键")
    private Long parentId;
}
