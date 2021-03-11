package com.entdiy.common.exception;

import lombok.Getter;

public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = -23347847086757165L;

    @Getter
    private String code;

    /**
     * 标识忽略全局的日志记录，一般用于一些用户输入数据校验失败类异常
     */
    @Getter
    private boolean skipLog = false;

    public ErrorCodeException(String message) {
        super(message);
    }

    public ErrorCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorCodeException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code(code);
    }

    public ErrorCodeException code(String code) {
        this.code = code;
        return this;
    }

    public ErrorCodeException skipLog() {
        this.skipLog = true;
        return this;
    }
}
