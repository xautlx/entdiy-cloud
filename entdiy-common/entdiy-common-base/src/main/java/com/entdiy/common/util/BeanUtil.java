package com.entdiy.common.util;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;

import java.time.LocalDate;

/**
 * 简层次封装Bean帮助类，便于后期根据效率需求自由切换Spring BeanUtils或BeanCopier等组件
 */
public class BeanUtil {

    private static ModelMapper modelMapper = new ModelMapper();
    private static Configuration configuration = modelMapper.getConfiguration();

    private static Converter<?, ?> propertyConverter = context -> {
        Object source = context.getSource();
        if (source != null) {
            if (source instanceof LocalDate) {
                return LocalDate.MIN.equals(source) ? null : context.getSource();
            }
        }
        return context.getSource();
    };

    static {
        configuration.setPropertyCondition(Conditions.isNotNull());
    }

    /**
     * bean属性拷贝
     *
     * @param source
     * @param target
     */
    public static <E> E copyProperties(Object source, E target) {
        if (source == null || target == null) {
            return null;
        }
        Class sourceType = source.getClass();
        Class targetType = target.getClass();
        TypeMap typeMap = modelMapper.getTypeMap(sourceType, targetType);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(sourceType, targetType);
            typeMap.setPropertyConverter(propertyConverter);
        }
        modelMapper.map(source, target);
        return target;
    }
}
