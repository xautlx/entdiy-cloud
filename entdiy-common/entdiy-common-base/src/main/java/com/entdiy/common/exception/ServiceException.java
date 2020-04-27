package com.entdiy.common.exception;

/**
 * 在Service层处理抛出的异常
 */
public class ServiceException extends ErrorCodeException {

    public ServiceException(String code, String message) {
        super(code, message);
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
