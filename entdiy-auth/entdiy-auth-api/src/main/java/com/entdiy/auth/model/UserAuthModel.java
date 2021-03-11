package com.entdiy.auth.model;

import com.entdiy.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@ApiModel(value = "User认证对象")
@Data
public class UserAuthModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户生效日期")
    private LocalDate validStartDate;

    @ApiModelProperty(value = "账户截止日期")
    private LocalDate validEndDate;

    @ApiModelProperty(value = "登录账号")
    private String accountName;

    @ApiModelProperty(value = "账号未锁定")
    private Boolean accountNonLocked = Boolean.TRUE;

    @ApiModelProperty(value = "密码过期日期")
    private LocalDate credentialsExpireDate;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "所属部门")
    private Long departmentId;
}
