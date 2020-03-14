package com.entdiy.common.model;

import lombok.Getter;

public enum ResultCodeEnum implements CodeMessageBean {

    OK("200", "Success");

    @Getter
    private String code;
    @Getter
    private String message;

    @Getter
    private Object[] params;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultCodeEnum params(Object... params) {
        this.params = params;
        return this;
    }
}
