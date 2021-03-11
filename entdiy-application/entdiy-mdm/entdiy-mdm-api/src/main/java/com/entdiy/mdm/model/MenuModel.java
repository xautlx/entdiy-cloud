package com.entdiy.mdm.model;

import com.entdiy.common.model.BaseTreeModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Menu对象")
@Data
public class MenuModel extends BaseTreeModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "名称路径", notes = "用于标记菜单数据路径标识")
    private String namePath;

    @ApiModelProperty(value = "菜单访问URL")
    private String url;

    @ApiModelProperty(value = "菜单前端组件")
    private String component;

    @ApiModelProperty(value = "图标样式")
    private String icon;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "展开标识")
    private Boolean initOpen;

}
