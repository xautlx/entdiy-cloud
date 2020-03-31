package com.entdiy.common.model;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

/**
 * <p></p>
 * 基础转换类，提供基本的几个方法，直接继承就可以，如果有需要写Mappings的写在 {@link #entityToDto(Object)} 方法上
 * 并且接口类上一定要加上 {@link org.mapstruct.Mapper} 注解
 */
public interface BaseModelMapper<ENTITY, DTO> {
    /**
     * 如有需要自定义该注解即可
     * 例如：
     *
     * @Mappings({
     * @Mapping(source = "code", target = "categoryCode"),
     * @Mapping(source = "name", target = "categoryName")
     * })
     * <p></p>
     * 重写此注解时一定要注意 返回值（DTO） 和 参数（ENTITY） 的顺序
     */
    @Mappings({})
    @InheritConfiguration
    DTO entityToDto(ENTITY entity);

    @InheritInverseConfiguration
    void mergeDtoToEntity(DTO dto, @MappingTarget ENTITY entity);

}