/**
 * Copyright © 2015 - 2017 EntDIY JavaEE Development Framework
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.entdiy.common.web.databind.resolver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.entdiy.common.constant.QueryConditionTypeEnum;
import com.entdiy.common.exception.ValidationException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 构建查询过滤参数对象
 */
public class QueryWrapperMethodAArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String PARAM_ORDER_BY = "order_by";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.getParameterType().isAssignableFrom(QueryWrapper.class));
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        QueryWrapper<?> queryWrapper = Wrappers.query();

        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap != null) {
            for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
                String paramName = param.getKey();
                String paramValue = param.getValue()[0];
                if (PARAM_ORDER_BY.equals(paramName)) {
                    String[] multiOrderBys = StringUtils.split(paramValue, ",");
                    for (String multiOrderBy : multiOrderBys) {
                        String[] splits = StringUtils.split(multiOrderBy.trim(), " ");
                        if (splits.length > 1 && "asc".equalsIgnoreCase(splits[1])) {
                            queryWrapper.orderByAsc(splits[0]);
                        } else {
                            queryWrapper.orderByDesc(splits[0]);
                        }
                    }
                } else if (paramName.startsWith("~")) {
                    paramName = StringUtils.substringAfter(paramName, "~");
                    String queryCondition = StringUtils.substringAfter(paramName, "^");
                    if (StringUtils.isEmpty(queryCondition)) {
                        queryCondition = QueryConditionTypeEnum.EQUAL.getValue();
                    }
                    String[] queryFields = StringUtils.split(StringUtils.substringBefore(paramName, "^"), "|");
                    if (queryFields.length > 1) {
                        String finalQueryCondition = queryCondition;
                        queryWrapper.and(wrapper -> {
                            for (String queryField : queryFields) {
                                queryWrapperCondition(wrapper, finalQueryCondition, queryField, paramValue);
                                wrapper.or();
                            }
                        });
                    } else {
                        queryWrapperCondition(queryWrapper, queryCondition, queryFields[0], paramValue);
                    }
                }
            }
        }
        return queryWrapper;
    }

    private void queryWrapperCondition(QueryWrapper<?> queryWrapper, String condition, String queryField, String value) {
        try {
            if (QueryConditionTypeEnum.IS_BLANK.getValue().equals(condition)) {
                //对于字符串类型空查询，转换为 X is null OR X=='' 组合条件
                queryWrapper.isNull(queryField);
                queryWrapper.or();
                queryWrapper.eq(queryField, "");
            } else if (QueryConditionTypeEnum.IS_NOT_BLANK.getValue().equals(condition)) {
                //对于字符串类型非空查询，转换为 X is not null AND X!='' 组合条件
                queryWrapper.isNotNull(queryField);
                queryWrapper.ne(queryField, "");
            } else {
                //特殊查询字符串转换处理
                if ("true".equals(value) || "false".equals(value)) {
                    //true 或 false 字符串转换对应Boolean类型，MyBatis层面会按照数据类型转换为1或0查询
                    MethodUtils.invokeMethod(queryWrapper, condition, queryField, Boolean.valueOf(value));
                } else {
                    MethodUtils.invokeMethod(queryWrapper, condition, queryField, value);
                }
            }
        } catch (NoSuchMethodException e) {
            throw new ValidationException("无效的条件表达式: " + condition);
        } catch (IllegalAccessException e) {
            throw new ValidationException("无效的条件表达式: " + condition);
        } catch (InvocationTargetException e) {
            throw new ValidationException("无效的条件表达式: " + condition);
        }
    }

    @Data
    public static class QueryWrapperParamStruct {
        @ApiModelProperty(value = "字段排序", name = PARAM_ORDER_BY, notes = "支持逗号分隔多字段排序，如 orderBy=type asc, id desc")
        private String orderBy;

    }
}
