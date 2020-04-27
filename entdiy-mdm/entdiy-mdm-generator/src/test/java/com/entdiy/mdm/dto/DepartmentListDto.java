package com.entdiy.mdm.dto;

import com.entdiy.common.dto.BaseTreeListDto;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @see com.entdiy.mdm.entity.Department
 */
@Data
public class DepartmentListDto extends BaseTreeListDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门代码")
    @ExcelProperty(value = "部门代码")
    private String code;

    @ApiModelProperty(value = "部门名称")
    @ExcelProperty(value = "部门名称")
    private String name;


}
