package com.entdiy.common.exception;

/**
 * 在Service层处理抛出的异常
 */
public class ServiceException extends ErrorCodeException {

    public ServiceException(String message) {
        this(message, null);
    }

    public ServiceException(String message, Throwable cause) {
        this("SERVICE_EXCEPTION", message, cause);
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
