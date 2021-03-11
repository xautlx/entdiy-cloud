package com.entdiy.mdm.model;

import com.entdiy.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="Permission接口对象")
@Data
public class PermissionModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "请求方法")
    private String requestMethod;

    @ApiModelProperty(value = "请求URI")
    private String requestUri;

}
