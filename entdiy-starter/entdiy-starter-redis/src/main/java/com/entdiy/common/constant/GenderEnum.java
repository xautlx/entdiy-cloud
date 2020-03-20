package com.entdiy.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.entdiy.common.model.LabelValueBean;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderEnum implements LabelValueBean {

    MALE(1, "男"),
    FEMALE(2, "女"),
    PRIVACY(4, "保密");

    @EnumValue
    @Getter
    private Integer code;

    @Getter
    private String label;

    @JsonCreator
    GenderEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }
}
