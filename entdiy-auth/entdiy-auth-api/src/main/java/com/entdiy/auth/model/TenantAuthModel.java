package com.entdiy.auth.model;

import com.entdiy.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;


@ApiModel(value = "Tenant认证对象")
@Data
public class TenantAuthModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号未锁定")
    private Boolean accountNonLocked;

    @ApiModelProperty(value = "账户生效日期")
    private LocalDate validStartDate;

    @ApiModelProperty(value = "账户截止日期")
    private LocalDate validEndDate;

    @ApiModelProperty(value = "租户短码")
    private String tenantCode;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

}
