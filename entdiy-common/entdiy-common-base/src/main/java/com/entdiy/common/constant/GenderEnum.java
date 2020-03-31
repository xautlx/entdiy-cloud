package com.entdiy.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.entdiy.common.model.LabelValueBean;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderEnum implements LabelValueBean {

    MALE("M", "男"),
    FEMALE("F", "女"),
    PRIVACY("P", "保密");

    @EnumValue
    @Getter
    private String code;

    @Getter
    private String label;

    @JsonCreator
    GenderEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
