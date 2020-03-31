package com.entdiy.common.exception;

import lombok.Getter;

public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = -23347847086757165L;

    @Getter
    private String errorCode;

    /**
     * 标识忽略全局的日志记录，一般用于一些用户输入数据校验失败类异常
     */
    @Getter
    private boolean skipLog = false;

    public ErrorCodeException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }

    public ErrorCodeException(ErrorCodeEnum errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode.getCode();
    }

    public ErrorCodeException skipLog() {
        this.skipLog = true;
        return this;
    }
}