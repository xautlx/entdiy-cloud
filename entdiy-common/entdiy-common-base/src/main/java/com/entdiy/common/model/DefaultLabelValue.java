package com.entdiy.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel(value = "Key-Value键值对数据", description = "诸如下拉框列表数据集合")
public class DefaultLabelValue implements LabelValueBean {

    @ApiModelProperty(value = "存取数据值")
    private String value;

    @ApiModelProperty(value = "显示文本")
    private String label;

    @ApiModelProperty(value = "扩展数据")
    private Map<String, Object> data;
}
