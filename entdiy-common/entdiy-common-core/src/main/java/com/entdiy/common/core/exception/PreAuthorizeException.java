package com.entdiy.common.core.exception;

/**
 * 权限异常
 */
public class PreAuthorizeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PreAuthorizeException() {
        super("Pre Authorize failure");
    }
}
