package com.entdiy.common.exception;

/**
 * 校验失败异常
 * 此类异常主要用于一些参数必要性校验，一个特点是全局的异常捕捉处理不做log.error记录
 */
public class ValidationException extends ErrorCodeException {

    public final static String DEFAULT_ERROR_CODE="VALIDATION_EXCEPTION";

    public ValidationException(String message) {
        this(message, null);
    }

    public ValidationException(String message, Throwable cause) {
        this("VALIDATION_EXCEPTION", message, cause);
    }

    public ValidationException(String code, String message, Throwable cause) {
        super(code, message, cause);
        this.skipLog();
    }
}
