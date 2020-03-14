package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
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
    private Long departmentId;

    @Column()
    private String userName;

    private String mobile;

    private String email;
}
