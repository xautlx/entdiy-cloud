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
import com.entdiy.common.entity.BaseAuditableEntity;
import com.entdiy.common.entity.BaseEntity;
import com.entdiy.common.model.LabelValueBean;
import com.entdiy.common.web.json.JsonViews;
import com.entdiy.mdm.constant.ELEMENT_TYPE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@TableName("sys_dict_item")
@Entity
@Table(name = "sys_dict_item", uniqueConstraints = @UniqueConstraint(columnNames = {"dict_id", "dictKey"}))
@ApiModel(value = "字典列表数据")
public class DictItem extends BaseEntity implements LabelValueBean {

    @ManyToOne(targetEntity = Dict.class)
    @JoinColumn(name = "dict_id", foreignKey = @ForeignKey(name = "fk_mdm_dict_item_dict_id", value = ConstraintMode.PROVIDER_DEFAULT))
    @ApiModelProperty(value = "所属主对象")
    @JsonView({JsonViews.AdminReadWrite.class})
    private Long dictId;

    @JsonIgnore
    @ApiModelProperty(value = "元素类型", notes = "冗余属性便于计算返回value值")
    @Column(nullable = false, length = 128)
    @Enumerated
    @JsonView({JsonViews.AdminReadWrite.class})
    private ELEMENT_TYPE elementType;

    @ApiModelProperty(value = "代码")
    @Column(nullable = false, length = 64, unique = true)
    @JsonView({JsonViews.AdminReadWrite.class})
    private String dictKey;

    @ApiModelProperty(value = "单文本值")
    @Column(nullable = true, length = 128)
    @JsonView({JsonViews.AdminReadWrite.class})
    private String simpleTextValue;

    @ApiModelProperty(value = "大文本值")
    @Lob
    @Column(nullable = true)
    @JsonView({JsonViews.AdminReadWrite.class})
    private String richTextValue;

    @ApiModelProperty(value = "启用")
    @Column(nullable = false, columnDefinition = BaseAuditableEntity.BOOLEAN_TRUE_COLUMN_DEFINITION)
    @JsonView({JsonViews.AdminReadWrite.class})
    private Boolean enabled;

    @ApiModelProperty(value = "描述")
    @Column(nullable = true, length = 1024)
    @JsonView({JsonViews.AdminReadWrite.class})
    private String remark;

    @ApiModelProperty(value = "相对排序号")
    @JsonView({JsonViews.AdminReadWrite.class})
    @Column(nullable = false)
    private Integer sortOrder = 100;

    @Override
    @JsonView({JsonViews.ReadOnly.class})
    public String getValue() {
        return dictKey;
    }

    @Override
    @JsonView({JsonViews.ReadOnly.class})
    public String getLabel() {
        return simpleTextValue;
    }
}
