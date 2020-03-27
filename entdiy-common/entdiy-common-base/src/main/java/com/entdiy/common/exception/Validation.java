package com.entdiy.common.exception;


/**
 * 校验失败异常
 * 此类异常主要用于一些参数必要性校验，一个特点是全局的异常捕捉处理不做log.error记录
 *
 * @see ValidationException
 */

public class Validation {
    public static void isTrue(boolean val, String message) {
        if (!val) {
            throw new ValidationException(ErrorCodeEnum.Validation.getCode(), message);
        }
    }
}
