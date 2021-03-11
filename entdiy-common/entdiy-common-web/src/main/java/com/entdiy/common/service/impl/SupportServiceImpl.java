package com.entdiy.common.service.impl;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.EnumUtil;
import com.entdiy.common.constant.QueryConditionTypeEnum;
import com.entdiy.common.exception.Validation;
import com.entdiy.common.model.DefaultLabelValue;
import com.entdiy.common.model.LabelValueBean;
import com.entdiy.common.service.ISupportService;
import com.entdiy.common.web.AppConfigProperties;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.entdiy.common.constant.BaseConstant.SPRING_CACHE_NAME_PREFIX;

@Slf4j
public class SupportServiceImpl implements ISupportService {

    @Autowired
    private AppConfigProperties appConfigProperties;

    private String getAppScanPackage() {
        if (StringUtils.isEmpty(appConfigProperties.getScanPackages())) {
            return "com.entdiy";
        }
        return appConfigProperties.getScanPackages();
    }

    @Override
    @Cacheable(cacheNames = SPRING_CACHE_NAME_PREFIX + "LabelValueEnums")
    public Map<String, List<LabelValueBean>> buildLabelValueEnumsCacheable() {
        Map<String, List<LabelValueBean>> data = Maps.newHashMap();
        Set<Class<?>> labelValueBeans = ClassUtil.scanPackageBySuper(getAppScanPackage(), LabelValueBean.class);
        labelValueBeans.forEach(clazz -> {
            log.debug("Processing enum: {}", clazz);
            if (EnumUtil.isEnum(clazz)) {
                Object[] enums = clazz.getEnumConstants();
                //已知发现Enum对象通过Jackson序列化存储到Redis缓存后，取回反序列化出现异常，因此调整转换为DefaultLabelValue存取
                data.put(clazz.getSimpleName(), Arrays.stream(enums).map(one -> {
                    LabelValueBean labelValueBean = (LabelValueBean) one;
                    DefaultLabelValue storage = new DefaultLabelValue();
                    storage.setLabel(labelValueBean.getLabel());
                    storage.setValue(labelValueBean.getValue());
                    return storage;
                }).collect(Collectors.toList()));
            }
        });

        QueryConditionTypeEnum[] queryConditionTypes = QueryConditionTypeEnum.values();
        List<LabelValueBean> queryConditionTypeLabelValueBeans = Lists.newArrayList();
        Arrays.stream(queryConditionTypes).forEach(item -> {
            DefaultLabelValue storage = new DefaultLabelValue();
            storage.setLabel(item.getLabel());
            storage.setValue(item.getValue());
            Map<String, Object> queryConditionData = Maps.newHashMap();
            queryConditionData.put("matchType", item.getMatchType());
            storage.setData(queryConditionData);
            queryConditionTypeLabelValueBeans.add(storage);
        });
        data.put("QueryConditionTypeEnum", queryConditionTypeLabelValueBeans);

        return data;
    }

    @Override
    @Cacheable(cacheNames = SPRING_CACHE_NAME_PREFIX + "ModelMetaData")
    public ModelMetaData buildModelMetaDataCacheable(String metaClass) {
        //一种方式如果不介意前端代码暴露企业敏感信息可以直接传入完整包路径类名，同时考虑到一些有意隐藏完整包名的需求，做了一定兼容设计传入类名及紧邻包名格式，如mdm.UserEditDto
        Set<Class<?>> modelClasses = ClassUtil.scanPackage(getAppScanPackage(), clazz -> clazz.getName().endsWith("." + metaClass));
        Validation.isTrue(modelClasses.size() == 1, "未唯一匹配类标识：{}，数量：{}", metaClass, modelClasses.size());
        Class<?> modelClass = modelClasses.iterator().next();
        Map<String, Object> meteData = Maps.newHashMap();

        GridOption gridOption = new GridOption();
        Map<String, Object> editRules = Maps.newHashMap();
        gridOption.setEditRules(editRules);
//        List<GridOptionColumn> columns =Lists.newArrayList();
//        gridOption.setColumns(columns);

        Field[] fields = ClassUtil.getDeclaredFields(modelClass);
        for (Field field : fields) {
            Class fieldType = field.getType();


//            GridOptionColumn gridColumn = new GridOptionColumn();
//            gridColumn.setField(field.getName());
//
//            GridOptionColumnEditRender editRender = new GridOptionColumnEditRender();
//            editRender.setName("input");
//            if (fieldType.equals(LocalDate.class)) {
//                editRender.setName("$input");
//            }

            List<Map<String, Object>> rules = Lists.newArrayList();
            int max = 0;
            //根据Hibernate注解的字符串类型和长度设定是否列表显示
            Column column = field.getAnnotation(Column.class);
            if (column != null && column.nullable() == false) {
                Map<String, Object> rule = Maps.newHashMap();
                rule.put("required", true);
                rule.put("message", "必填项目");
                rules.add(rule);

                if (fieldType.equals(String.class) && column.length() > 0) {
                    max = column.length();
                }
            }

            Length length = field.getAnnotation(Length.class);
            if (length != null) {
                if (length.min() > 0) {
                    Map<String, Object> rule = Maps.newHashMap();
                    rule.put("min", length.min());
                    rule.put("message", "最小长度: " + length.min());
                    rules.add(rule);
                }
                //Validation注解优先级高可覆盖column.length()值
                if (length.max() > 0) {
                    max = length.max();
                }
            }

            if (max > 0) {
                Map<String, Object> rule = Maps.newHashMap();
                rule.put("max", max);
                rule.put("message", "最大长度: " + max);
                rules.add(rule);
            }

            if (CollectionUtils.isNotEmpty(rules)) {
                editRules.put(field.getName(), rules);
            }
        }

        ModelMetaData modelMetaData = new ModelMetaData();
        modelMetaData.setMetaClass(metaClass);
        modelMetaData.setModelClass(modelClass);
        modelMetaData.setMeteData(gridOption);
        return modelMetaData;
    }

    @Data
    public static class GridOption {
        private Map<String, Object> editRules;
    }

    @Data
    public static class GridOptionColumn {
        private String field;
        private GridOptionColumnEditRender editRender;
    }

    @Data
    public static class GridOptionColumnEditRender {
        private String name;
        private GridOptionColumnEditRenderProps props;
    }

    @Data
    public static class GridOptionColumnEditRenderProps {
        private String type;
    }
}
