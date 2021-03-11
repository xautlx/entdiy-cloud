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
import com.entdiy.common.web.json.JsonViews;
import com.entdiy.mdm.constant.ELEMENT_TYPE;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@TableName("sys_dict")
@Entity
@Table(name = "sys_dict")
@ApiModel(value = "字典主数据")
public class Dict extends BaseEntity {

    @ApiModelProperty(value = "代码")
    @Column(nullable = false, length = 64, unique = true)
    @JsonView({JsonViews.AdminReadWrite.class,JsonViews.AppReadOnly.class})
    private String code;

    @ApiModelProperty(value = "名称")
    @Column(nullable = false, length = 128)
    @JsonView({JsonViews.AdminReadWrite.class,JsonViews.AppReadOnly.class})
    private String name;

    @ApiModelProperty(value = "元素类型")
    @Column(nullable = false, length = 128)
    @Enumerated
    @JsonView({JsonViews.AdminReadWrite.class})
    private ELEMENT_TYPE elementType;

    @ApiModelProperty(value = "描述")
    @Column(nullable = true, length = 1024)
    @JsonView({JsonViews.AdminReadWrite.class})
    private String remark;
}
