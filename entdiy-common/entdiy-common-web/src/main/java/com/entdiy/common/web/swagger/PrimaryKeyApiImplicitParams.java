package com.entdiy.common.web.swagger;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.*;

/**
 * id主键的API参数定义
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "id",
                value = "主键",
                dataTypeClass = String.class,
                paramType = "query"
        )
})
public @interface PrimaryKeyApiImplicitParams {
}
