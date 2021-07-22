package com.entdiy.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.entdiy.common.core.annotation.Excel;
import com.entdiy.common.core.annotation.Excel.ColumnType;
import com.entdiy.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 参数配置表 sys_config
 *
 * 
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_config")
public class SysConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 参数主键
     */
    @Excel(name = "参数主键", cellType = ColumnType.NUMERIC)
    @TableId(value = "config_id", type = IdType.AUTO)
    private Long configId;

    /**
     * 参数名称
     */
    @Excel(name = "参数名称")
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
    private String configName;

    /**
     * 参数键名
     */
    @Excel(name = "参数键名")
    @NotBlank(message = "参数键名长度不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
    private String configKey;

    /**
     * 参数键值
     */
    @Excel(name = "参数键值")
    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;

}
