package com.entdiy.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "data", "eid", "signature", "nonce", "timestamp"})
@ApiModel("Restful JSON 响应结构")
public class ViewResult<T> {

    @ApiModelProperty(value = "标识代码", notes = "200标识成功，其余为异常情况")
    private String code;

    @ApiModelProperty(value = "结果消息", notes = "异常情况对于代码的补充消息说明")
    private String message;

    @ApiModelProperty(value = "业务数据")
    private T data;

    @ApiModelProperty(
            value = "异常流水号（动态输出属性）",
            notes = "Error ID: 异常流水号，logger日志记录到系统。" +
                    "前端可展现此错误ID给用户，用户向系统管理员反馈问题可提供此EID，便于到日志系统查询获取对应错误日志明细。" +
                    "此属性值为可选项，如参数校验类异常不做日志记录，此时不生成eid属性值。")
    private String eid;

    @JsonIgnore
    private boolean skipLog = false;

    public static ViewResult success() {
        ViewResult that = new ViewResult();
        that.setCode(ResultCodeEnum.OK.getCode());
        that.setMessage(ResultCodeEnum.OK.getMessage());
        return that;
    }

    public static <T> ViewResult success(T data) {
        ViewResult that = success();
        that.setData(data);
        return that;
    }
}
