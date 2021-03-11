package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseAuditableEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@TableName("sys_tenant")
@Entity
@Table(name = "sys_tenant")
@ApiModel(value = "租户")
public class Tenant extends BaseAuditableEntity {

    @Column(length = 64, nullable = false, unique = true)
    @ApiModelProperty(value = "租户短码", notes = "用于登录界面输入")
    private String tenantCode;

    @Column(length = 128, nullable = false)
    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @Column(length = 1024, nullable = true)
    @ApiModelProperty(value = "备注说明")
    private String remark;

    @ApiModelProperty(value = "账号未锁定")
    @Column(nullable = false, columnDefinition = BaseAuditableEntity.BOOLEAN_TRUE_COLUMN_DEFINITION)
    private Boolean accountNonLocked;

    @ApiModelProperty(value = "账户生效日期")
    private LocalDate validStartDate;

    @ApiModelProperty(value = "账户截止日期")
    private LocalDate validEndDate;


}
