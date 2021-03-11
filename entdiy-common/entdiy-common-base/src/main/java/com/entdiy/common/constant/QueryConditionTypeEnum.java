package com.entdiy.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QueryConditionTypeEnum {

    LIKE("like", "包含 (%X%)", "text"),
    LIKE_LEFT("likeLeft", "以..结尾 (%X)", "text"),
    LIKE_RIGHT("likeRight", "以..开头 (X%)", "text"),
    EQUAL("eq", "等于 (=X)", "all"),
    NOT_EQUAL("ne", "不等于 (!=X)", "all"),
    GREAT_THAN("gt", "大于 (>X)", "number,date"),
    GREAT_EQUAL("ge", "大于等于 (>=X)", "number,date"),
    LESS_THAN("lt", "小于 (<X)", "number,date"),
    LESS_EQUAL("le", "小于等于 (<=X)", "number,date"),
    BETWEEN("between", "介于之间 (between X and Y)", "number,date"),
    IS_NULL("isNull", "无值 (is null)", "all"),
    IS_NOT_NULL("isNotNull", "有值 (is not null)", "all"),
    IS_BLANK("isBlank", "空值 (is null OR ='')", "text"),
    IS_NOT_BLANK("isNotBlank", "非空值 (is not null AND !='')", "text");

    @Getter
    @EnumValue
    @JsonProperty
    private String value;

    @Getter
    @JsonProperty
    private String label;

    @Getter
    @ApiModelProperty(value = "适配元素类型", notes = "可逗号分隔多个")
    private String matchType;

    @JsonCreator
    QueryConditionTypeEnum(String value, String label, String matchType) {
        this.value = value;
        this.label = label;
        this.matchType = matchType;
    }


}
