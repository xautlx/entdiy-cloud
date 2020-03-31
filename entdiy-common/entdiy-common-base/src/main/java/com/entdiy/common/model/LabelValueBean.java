package com.entdiy.common.model;

public interface LabelValueBean {

    String getCode();

    String getLabel();

    default Object[] getParams() {
        return null;
    }
}
