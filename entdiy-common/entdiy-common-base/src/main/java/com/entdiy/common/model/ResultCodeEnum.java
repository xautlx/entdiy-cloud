package com.entdiy.common.model;

import lombok.Getter;

public enum ResultCodeEnum {

    OK("200", "OK"),
    Error("500", "Internal Server Error"),
    Validation("800", "Validation Error"),
    DataConstraint("810", "Database Data Constraint Error"),
    AccessDenied("401", "Access Denied");

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
