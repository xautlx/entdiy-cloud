/**
 * Copyright © 2015 - 2017 EntDIY JavaEE Development Framework
 *
 * Site: https://www.entdiy.com, E-Mail: xautlx@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@TableName("auth_role_permission")
@Entity
@Table(name = "auth_role_permission", uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"}))
@ApiModel(value = "角色权限关联")
public class RolePermission extends BaseEntity {

    @ApiModelProperty(value = "角色主键")
    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_auth_role_permission_role_id", value = ConstraintMode.PROVIDER_DEFAULT))
    private Long roleId;

    @ApiModelProperty(value = "权限主键")
    @ManyToOne(targetEntity = Permission.class)
    @JoinColumn(name = "permission_id", foreignKey = @ForeignKey(name = "fk_auth_role_permission_permission_id", value = ConstraintMode.PROVIDER_DEFAULT))
    private Long permissionId;
}
