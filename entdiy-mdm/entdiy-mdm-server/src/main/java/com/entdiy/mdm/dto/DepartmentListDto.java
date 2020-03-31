package com.entdiy.mdm.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.entdiy.common.dto.BaseTreeListDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


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