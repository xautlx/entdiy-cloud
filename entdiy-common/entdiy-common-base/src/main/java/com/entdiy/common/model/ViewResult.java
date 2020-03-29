package com.entdiy.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"eid", "code", "name", "message", "data", "signature", "nonce", "timestamp"})
public class ViewResult<T> {

    private boolean success;

    private String code;

    private String message;

    private T data;

    public static ViewResult success() {
        ViewResult that = new ViewResult();
        that.setSuccess(true);
        return that;
    }

    public static <T> ViewResult success(T data) {
        ViewResult that = new ViewResult();
        that.setSuccess(true);
        that.setData(data);
        return that;
    }

    public static ViewResult success(CodeMessageBean codeMessage) {
        ViewResult that = success();
        that.setCode(codeMessage.getCode());
        that.setMessage(codeMessage.getMessage());
        return that;
    }
}
