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

import com.entdiy.common.exception.Validation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 构建分页排序参数对象
 */
public class PageableMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final static int DEFAULT_PAGE_SIZE = 10;

    private final static int MAX_PAGE_SIZE = 2000;

    private final static String PARAM_PAGEABLE_NUMBER = "page_number";

    private final static String PARAM_PAGEABLE_SIZE = "page_size";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (Pageable.class.equals(parameter.getParameterType()));
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        int page = 0;
        String pageParam = request.getParameter(PARAM_PAGEABLE_NUMBER);
        if (StringUtils.isNotBlank(pageParam)) {
            //前端从1开始，JPA Page从0开始
            page = Integer.valueOf(pageParam) - 1;
        }

        int size = DEFAULT_PAGE_SIZE;
        String sizeParam = request.getParameter(PARAM_PAGEABLE_SIZE);
        if (StringUtils.isNotBlank(sizeParam)) {
            size = Integer.valueOf(sizeParam);
            Validation.isTrue(size <= MAX_PAGE_SIZE, "单页查询数量超过限制: {}", MAX_PAGE_SIZE);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return pageRequest;
    }

    @Data
    public static class PageableParamStruct {
        @ApiModelProperty(value = "分页当前页码", name = PARAM_PAGEABLE_NUMBER, notes = "默认起始：1")
        private int pageNumber;

        @ApiModelProperty(value = "每页记录数", name = PARAM_PAGEABLE_SIZE, notes = "默认值：" + DEFAULT_PAGE_SIZE)
        private int pageSize;
    }

    @Data
    public static class PageResponseStruct<DTO> {
        @ApiModelProperty(value = "分页数据")
        private List<DTO> content;

        @ApiModelProperty(value = "分页当前页码")
        private int pageNumber;

        @ApiModelProperty(value = "总页数")
        private int totalPages;

        @ApiModelProperty(value = "总记录数")
        private long totalElements;
    }
}
