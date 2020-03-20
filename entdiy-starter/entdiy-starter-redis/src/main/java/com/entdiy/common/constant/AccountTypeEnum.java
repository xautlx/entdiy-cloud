package com.entdiy.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.entdiy.common.model.LabelValueBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 用户账户类型
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccountTypeEnum implements LabelValueBean {

    SYSTEM(1, "系统", true),
    EMAIL(2, "邮件", true),
    MOBILE(4, "手机", true),
    WECHAT(8, "微信", false);

    @EnumValue
    @Getter
    private Integer code;

    @Getter
    private String label;

    @ApiModelProperty("标识是否可前端进行管理维护")
    @Getter
    private Boolean manageable;

    @JsonCreator
    AccountTypeEnum(Integer code, String label, Boolean manageable) {
        this.code = code;
        this.label = label;
        this.manageable = manageable;
    }
}
