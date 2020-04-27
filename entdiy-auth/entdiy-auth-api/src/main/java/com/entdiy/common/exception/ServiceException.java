package com.entdiy.common.exception;

/**
 * 在Service层处理抛出的异常
 */
public class ServiceException extends ErrorCodeException {

    public ServiceException(ErrorCodeEnum errorCode) {
        super(errorCode);
    }

    public ServiceException(ErrorCodeEnum errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
