package com.entdiy.common.exception;


import org.apache.commons.lang3.StringUtils;

/**
 * 校验失败异常
 * 此类异常主要用于一些参数必要性校验，一个特点是全局的异常捕捉处理不做log.error记录
 *
 * @see ValidationException
 */

public class Validation {

    private static String processMessageParams(String message, Object... params) {
        if (params != null) {
            for (Object param : params) {
                message = StringUtils.replace(message, "{}", String.valueOf(param));
            }
        }
        return message;
    }

    public static void isTrue(boolean val, String message, Object... params) {
        if (!val) {
            throw new ValidationException(processMessageParams(message, params));
        }
    }

    public static <E> E notNull(E object, String message, Object... params) {
        if (object == null) {
            throw new ValidationException(processMessageParams(message, params));
        }
        return object;
    }

    public static String notBlank(String text, String message, Object... params) {
        if (text == null) {
            throw new ValidationException(processMessageParams(message, params));
        }
        text = text.trim();
        if (StringUtils.isEmpty(text)) {
            throw new ValidationException(processMessageParams(message, params));
        }
        return text;
    }

}
