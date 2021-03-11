package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseTreeEntity;
import com.entdiy.common.web.json.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
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
@TableName("auth_department")
@Entity
@Table(name = "auth_department")
@ApiModel(value = "部门")
public class Department extends BaseTreeEntity {

    @Column(length = 32, nullable = false, unique = true)
    @ApiModelProperty(value = "部门代码")
    @JsonView({JsonViews.AdminReadWrite.class, JsonViews.AppReadOnly.class})
    private String code;

    @Column(length = 128, nullable = false)
    @ApiModelProperty(value = "部门名称")
    @JsonView({JsonViews.AdminReadWrite.class, JsonViews.AppReadOnly.class})
    private String name;

    @Column(nullable = true)
    @ApiModelProperty(value = "组建日期")
    @JsonView({JsonViews.AdminReadWrite.class})
    private LocalDate establishDate;
}
