package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@TableName("mdm_user")
@Entity
@Table(name = "mdm_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_mdm_user_user_name",
                        columnNames = {"userName"}),
                @UniqueConstraint(
                        name = "uk_mdm_user_mobile",
                        columnNames = {"mobile"}),
                @UniqueConstraint(
                        name = "uk_mdm_user_email",
                        columnNames = {"email"})
        }
)
@ApiModel(value = "用户")
public class User extends BaseEntity {

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "fk_mdm_user_department_id"))
    @ApiModelProperty(value = "所属部门")
    private Long departmentId;

    @Column(length = 64, nullable = false)
    @ApiModelProperty(value = "登录账号")
    private String userName;

    @Column(length = 64, nullable = true)
    @ApiModelProperty(value = "姓名")
    private String realName;

    @Column(length = 32, nullable = true)
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @Column(length = 128, nullable = true)
    @ApiModelProperty(value = "电子邮件")
    private String email;
}
