package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseEntity;
import com.entdiy.mdm.constant.ELEMENT_TYPE;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;


@Setter
@Getter
@TableName("sys_config_param")
@Entity
@Table(name = "sys_config_param")
@ApiModel(value = "配置参数")
public class ConfigParam extends BaseEntity {

    @ApiModelProperty(value = "代码")
    @Column(length = 64, unique = true, nullable = false)
    private String code;

    @ApiModelProperty(value = "名称")
    @Column(length = 256, nullable = false)
    private String name;

    @ApiModelProperty(value = "元素类型")
    @Column(nullable = true, length = 128)
    @Enumerated(EnumType.STRING)
    private ELEMENT_TYPE elementType;

    @ApiModelProperty(value = "单文本值")
    @Column(nullable = true, length = 128)
    private String simpleTextValue;

    @ApiModelProperty(value = "大文本值")
    @Lob
    @Column(nullable = true)
    private String richTextValue;

    @ApiModelProperty(value = "参数属性用法说明")
    @Column(length = 2000)
    private String remark;
}
