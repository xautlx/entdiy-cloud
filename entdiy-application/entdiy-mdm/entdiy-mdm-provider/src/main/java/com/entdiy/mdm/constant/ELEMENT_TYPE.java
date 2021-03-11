package com.entdiy.mdm.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.entdiy.common.model.EnumValueBean;

/**
 * 标识属性元素类型，用于前端表单做对应编辑组件支持
 */
public enum ELEMENT_TYPE implements IEnum<Integer>, EnumValueBean {

    INPUT(10, "单行文本框"),
    TEXTAREA(20, "多行文本框"),
    RICH_TEXT(30, "富文本输入"),
    LOCAL_DATE(40, "日期选择"),
    LOCAL_DATETIME(50, "日期时间选择"),
    BOOLEAN(60, "是否选取");

    private Integer value;

    private String label;

    ELEMENT_TYPE(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
