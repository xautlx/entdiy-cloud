package com.entdiy.common.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = -23347847086757165L;

    @Getter
    private String code;

    /**
     * 标识忽略全局的日志记录，一般用于一些用户输入数据校验失败类异常
     */
    @Getter
    private boolean skipLog = false;


    public ErrorCodeException(String code, String message) {
        super(message);
        Assert.isTrue(StringUtils.isNotEmpty(message), "Error message required");
        this.code = code;
    }

    public ErrorCodeException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ErrorCodeException skipLog() {
        this.skipLog = true;
        return this;
    }
}
