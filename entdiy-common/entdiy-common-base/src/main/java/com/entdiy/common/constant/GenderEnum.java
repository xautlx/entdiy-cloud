package com.entdiy.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.entdiy.common.model.LabelValueBean;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderEnum implements LabelValueBean {

    MALE("M", "男"),
    FEMALE("F", "女"),
    PRIVACY("P", "保密");

    @Getter
    @EnumValue
    @JsonProperty
    private String value;

    @Getter
    @JsonProperty
    private String label;

    @JsonCreator
    GenderEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
