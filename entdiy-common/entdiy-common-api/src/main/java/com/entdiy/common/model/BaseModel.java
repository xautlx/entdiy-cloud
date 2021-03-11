package com.entdiy.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseModel {

    @Getter
    @Setter
    @ApiModelProperty(value = "主键")
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty(value = "乐观锁版本")
    private Integer version;
}
