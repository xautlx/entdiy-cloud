package com.entdiy.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

public class BaseTreeEditDto extends BaseEditDto {

    @ApiModelProperty(value = "上级节点主键")
    @TableField(value = "parent_id")
    private Long parentId;
}
