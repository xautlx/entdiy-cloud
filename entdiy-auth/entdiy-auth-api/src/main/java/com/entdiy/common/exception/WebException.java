package com.entdiy.common.exception;

/**
 * 在Web层处理抛出的异常
 */
public class WebException extends ErrorCodeException {

    public WebException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public WebException(ErrorCodeEnum errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
