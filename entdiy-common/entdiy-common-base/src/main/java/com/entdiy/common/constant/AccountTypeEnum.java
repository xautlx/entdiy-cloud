package com.entdiy.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.entdiy.common.model.LabelValueBean;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 用户账户类型
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccountTypeEnum implements LabelValueBean {

    SYSTEM("系统", true),
    EMAIL("邮件", true),
    MOBILE("手机", true),
    WECHAT("微信", false);

    @EnumValue
    @Getter
    private String code;

    @Getter
    private String label;

    @ApiModelProperty("标识是否可前端进行管理维护")
    @Getter
    private Boolean manageable;

    AccountTypeEnum(String label, Boolean manageable) {
        this.code = this.name();
        this.label = label;
        this.manageable = manageable;
    }

    @JsonCreator
    AccountTypeEnum(String code, String label, Boolean manageable) {
        this.code = code;
        this.label = label;
        this.manageable = manageable;
    }
}
