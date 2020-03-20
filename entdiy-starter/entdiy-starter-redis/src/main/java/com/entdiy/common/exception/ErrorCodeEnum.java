package com.entdiy.common.exception;

import com.entdiy.common.model.CodeMessageBean;
import lombok.Getter;

public enum ErrorCodeEnum implements CodeMessageBean {

    ERROR("500", "Internal Server Error");

    @Getter
    private String code;
    @Getter
    private String message;

    @Getter
    private Object[] params;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorCodeEnum params(Object... params) {
        this.params = params;
        return this;
    }
}
