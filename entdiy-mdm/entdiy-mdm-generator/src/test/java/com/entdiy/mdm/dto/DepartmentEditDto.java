package com.entdiy.mdm.dto;

import com.entdiy.common.dto.BaseTreeEditDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @see com.entdiy.mdm.entity.Department
 */
@Data
public class DepartmentEditDto extends BaseTreeEditDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门代码")
    private String code;

    @ApiModelProperty(value = "部门名称")
    private String name;


}
