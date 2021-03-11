package com.entdiy.common.model;

import io.swagger.annotations.ApiModelProperty;

public interface EnumValueBean {

    @ApiModelProperty(value = "存取数据值")
    Integer getValue();

    @ApiModelProperty(value = "显示文本")
    String getLabel();

}
