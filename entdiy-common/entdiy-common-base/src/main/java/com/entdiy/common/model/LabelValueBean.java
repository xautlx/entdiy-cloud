package com.entdiy.common.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

public interface LabelValueBean {

    @ApiModelProperty(value = "存取数据值")
    String getValue();

    @ApiModelProperty(value = "显示文本")
    String getLabel();

    @ApiModelProperty(value = "扩展数据")
    default Map<String, Object> getData() {
        return null;
    }
}
