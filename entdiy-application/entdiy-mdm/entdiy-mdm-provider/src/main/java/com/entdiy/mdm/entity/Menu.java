package com.entdiy.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.entity.BaseTreeEntity;
import com.entdiy.common.web.json.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@TableName("sys_menu")
@Entity
@Table(name = "sys_menu")
@ApiModel(value = "菜单")
public class Menu extends BaseTreeEntity {

    private static final long serialVersionUID = 2860233299443173932L;

    private static final Logger logger = LoggerFactory.getLogger(Menu.class);

    @ApiModelProperty(value = "名称")
    @Column(nullable = false, length = 32)
    @JsonView({JsonViews.AdminReadWrite.class})
    private String name;


    @ApiModelProperty(value = "名称路径", notes = "详见： ")
    @Column(nullable = true, length = 512)
    @JsonView({JsonViews.AdminReadOnly.class})
    private String namePath;

    @ApiModelProperty(value = "菜单访问URL")
    @Column(length = 256)
    @JsonView({JsonViews.AdminReadWrite.class, JsonViews.AdminReadOnly.class})
    private String url;

    @ApiModelProperty(value = "菜单前端组件")
    @Column(length = 256)
    @JsonView({JsonViews.AdminReadWrite.class, JsonViews.AdminReadOnly.class})
    private String component;

    @ApiModelProperty(value = "描述")
    @Column(length = 1000)
    @JsonView({JsonViews.AdminReadWrite.class})
    private String remark;

    @ApiModelProperty(value = "图标样式")
    @Column(length = 128)
    @JsonView({JsonViews.AdminReadWrite.class, JsonViews.AdminReadOnly.class})
    private String icon;

    @ApiModelProperty(value = "排序号", notes = "支持小数形式便于在两个紧挨数字之间插入新序号")
    @Column
    @JsonView({JsonViews.AdminReadWrite.class, JsonViews.AdminReadOnly.class})
    private Float orderRank;

    @ApiModelProperty(value = "展开标识", notes = "是否默认展开菜单组")
    @JsonView({JsonViews.AdminReadWrite.class, JsonViews.AdminReadOnly.class})
    private Boolean initOpen = Boolean.FALSE;

    @ApiModelProperty(value = "禁用标识", notes = "禁用项目用户端不显示")
    @JsonView({JsonViews.AdminReadWrite.class, JsonViews.AdminReadOnly.class})
    private Boolean disabled = Boolean.FALSE;

    @Override
    @Transient
    public String getDisplay() {
        return name;
    }
}
