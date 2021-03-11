package com.entdiy.common.exception;

/**
 * 在Web层处理抛出的异常
 */
public class WebException extends ErrorCodeException {

    public WebException(String message) {
        this(message, null);
    }

    public WebException(String message, Throwable cause) {
        this("WEB_EXCEPTION", message, cause);
    }

    public WebException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
