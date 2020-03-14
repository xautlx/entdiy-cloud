package com.entdiy.common.exception;

/**
 * 校验失败异常
 * 此类异常主要用于一些参数必要性校验，一个特点是全局的异常捕捉处理不做log.error记录
 */
public class ValidationException extends ErrorCodeException {

    public ValidationException(ErrorCodeEnum errorCode) {
        super(errorCode);
        skipLog();
    }

    public ValidationException(ErrorCodeEnum errorCode, Throwable cause) {
        super(errorCode, cause);
        skipLog();
    }
}
