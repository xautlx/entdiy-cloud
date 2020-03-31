package com.entdiy.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "Key-Value键值对数据", description = "诸如下拉框列表数据集合")
public class DefaultLabelValue implements LabelValueBean {

    @ApiModelProperty(value = "存取数据值")
    private String code;

    @ApiModelProperty(value = "显示文本")
    private String label;

    @ApiModelProperty(value = "显示文本占位符参数列表")
    private Object[] params;

    public LabelValueBean params(Object[] params) {
        this.setLabel(String.format(label, params));
        return this;
    }
}
