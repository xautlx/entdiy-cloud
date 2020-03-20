package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseTreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@TableName("mdm_department")
@Entity
@Table(name = "mdm_department")
@ApiModel(value = "部门")
public class Department extends BaseTreeEntity {

    @Column(length = 32, nullable = false, unique = true)
    @ApiModelProperty(value = "部门代码")
    private String code;

    @Column(length = 128, nullable = false)
    @ApiModelProperty(value = "部门名称")
    private String name;
}
