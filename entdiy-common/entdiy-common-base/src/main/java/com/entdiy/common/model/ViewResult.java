package com.entdiy.common.model;

import com.entdiy.common.auth.AuthDataHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * Restful JSON 响应结构
 *
 * 注意请勿随意修改 @JsonPropertyOrder 注解序列化顺序，部分功能采用字符串形式提取JSON结构数据项，如 SpringBootMVCTest
 *
 * @param <T> 业务对象泛型类型
 */
@Setter
@Getter
@ToString(exclude = "skipLog")
@JsonPropertyOrder({ViewResult.SUCCESS_KEY_NAME, "code", "message", "result", "timestamp"})
@ApiModel("Restful JSON 响应结构")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewResult<T> {

    protected ViewResult() {
    }

    public static final String SUCCESS_KEY_NAME = "success";

    @ApiModelProperty(value = "异常标识代码", notes = "取一个比较特殊的属性名称避免和业务属性冲突混淆")
    @JsonProperty(SUCCESS_KEY_NAME)
    private Boolean success;

    @ApiModelProperty(value = "标识代码", notes = "200标识成功，其余为异常情况")
    private String code;

    @ApiModelProperty(value = "结果消息", notes = "异常情况对于代码的补充消息说明")
    private String message;

    @ApiModelProperty(value = "业务数据")
    @JsonProperty("result")
    private T data;

    public static ViewResult success() {
        ViewResult that = new ViewResult();
        that.setSuccess(Boolean.TRUE);
        that.setCode(ResultCodeEnum.OK.getCode());
        that.setTimestamp(System.currentTimeMillis());
        return that;
    }

    public static <T> ViewResult success(String message, T data) {
        ViewResult that = success();
        that.setMessage(message);
        that.setData(data);
        return that;
    }

    public static <T> ViewResult success(T data) {
        ViewResult that = success();
        that.setData(data);
        return that;
    }

    public ViewResult message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * ERROR情况记录返回当前请求URL
     */
    private String path;

    private Long timestamp;


    /**
     * 认证相关数据
     */
    @JsonIgnore
    private AuthDataHolder.AuthData authData;

    @ApiModelProperty(
            value = "日志追踪流水号",
            notes = "异常流水号，logger日志记录到系统。" +
                    "前端可展现此错误ID给用户，用户向系统管理员反馈问题可提供此EID，便于到日志系统查询获取对应错误日志明细。" +
                    "此属性值为可选项，如参数校验类异常不做日志记录，此时不生成此属性值。")
    private String traceId;


    @JsonIgnore
    @Accessors(chain = true, fluent = true)
    private boolean skipLog = false;

    @ApiModelProperty(value = "忽略消息显示", notes = "控制前端不做默认弹出消息显示")
    @JsonProperty
    private Boolean skipMessage;

    public ViewResult<T> skipMessage(Boolean skipMessage) {
        this.skipMessage = skipMessage;
        return this;
    }

    @ApiModelProperty(value = "版本信息", notes = "扩展返回数据版本信息，可用于前端缓存比对判断")
    private String version;

    public ViewResult<T> version(String version) {
        this.version = version;
        return this;
    }

    public static ViewResult error(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static ViewResult error(HttpStatus httpStatus, String message) {
        return error(String.valueOf(httpStatus.value()), message);
    }

    public static ViewResult error(String errorCode, String message) {
        ViewResult that = new ViewResult();
        that.setSuccess(Boolean.FALSE);
        that.setCode(errorCode);
        that.setMessage(message);
        that.setTimestamp(System.currentTimeMillis());
        return that;
    }
}
