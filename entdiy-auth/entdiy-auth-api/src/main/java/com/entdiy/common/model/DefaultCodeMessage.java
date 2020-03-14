package com.entdiy.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Key-Value键值对数据", description = "诸如下拉框列表数据集合")
public class DefaultCodeMessage implements CodeMessageBean {

    @ApiModelProperty(value = "存取数据值")
    private String code;

    @ApiModelProperty(value = "显示文本")
    private String message;

    @ApiModelProperty(value = "message占位符参数列表")
    private Object[] params;

    public static DefaultCodeMessage build(String code, String message) {
        DefaultCodeMessage bean = new DefaultCodeMessage();
        bean.setCode(code);
        bean.setMessage(message);
        return bean;
    }

    public DefaultCodeMessage params(Object[] params) {
        this.setMessage(String.format(message, params));
        return this;
    }
}
