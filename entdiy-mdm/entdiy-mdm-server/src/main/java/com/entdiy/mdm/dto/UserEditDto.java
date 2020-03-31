package com.entdiy.mdm.dto;

import com.entdiy.common.dto.BaseEditDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserEditDto extends BaseEditDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "登录账号")
    private String userName;

    @ApiModelProperty(value = "所属部门")
    private Long departmentId;


}
