package com.entdiy.mdm.entity;

import com.entdiy.common.entity.BaseTreeEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author Li Xia
 * @since 2020-04-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mdm_department")
@ApiModel(value="Department对象", description="部门")
public class Department extends BaseTreeEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门代码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "部门名称")
    @TableField("name")
    private String name;


}
