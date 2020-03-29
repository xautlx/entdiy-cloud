package com.entdiy.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionViewResult<T> extends ViewResult<T> {

    /**
     * Error ID: 异常流水号，logger日志记录到系统。
     * 前端可展现此错误ID给用户，用户向系统管理员反馈问题可提供此EID，便于到日志系统查询获取对应错误日志明细。
     * 此属性值为可选项，如参数校验类异常不做日志记录，此时不生成eid属性值。
     */
    private String eid;

    @JsonIgnore
    private boolean skipLog = false;
}
