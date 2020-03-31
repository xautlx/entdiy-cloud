package com.entdiy.common.databind.annotation;


import java.lang.annotation.*;

/**
 * @see com.entdiy.common.dto.BaseEditDto
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EditDtoDataBinder {
    Class service() default Void.class;
}
