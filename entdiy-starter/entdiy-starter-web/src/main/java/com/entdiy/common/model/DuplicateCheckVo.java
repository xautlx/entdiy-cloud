package com.entdiy.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Data
@ApiModel(value = "重复校验数据模型")
public class DuplicateCheckVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "校验字段对")
    private Map<String, Object> fieldPairs;

    @ApiModelProperty(value = "数据ID")
    private String dataId;

}
