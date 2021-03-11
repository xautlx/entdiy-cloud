package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@TableName("auth_user")
@Entity
@Table(name = "auth_user")
@ApiModel(value = "用户")
public class User extends BaseEntity {

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "fk_mdm_user_department_id"))
    @ApiModelProperty(value = "所属部门")
    private Long departmentId;

    @Column(length = 64, nullable = false, unique = true)
    @ApiModelProperty(value = "登录账号")
    private String accountName;

    @Column(length = 64, nullable = true)
    @ApiModelProperty(value = "姓名")
    private String realName;

    @Column(length = 32, nullable = true, unique = true)
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @Column(length = 128, nullable = true, unique = true)
    @ApiModelProperty(value = "电子邮件")
    private String email;

    @Column(length = 64, nullable = false)
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "账户生效日期")
    private LocalDate validStartDate;

    @ApiModelProperty(value = "账户截止日期")
    private LocalDate validEndDate;

    @ApiModelProperty(value = "账号未锁定")
    private Boolean accountNonLocked;

    @ApiModelProperty(value = "密码过期日期")
    private LocalDate credentialsExpireDate;
}
