/**
 * Copyright © 2015 - 2017 EntDIY JavaEE Development Framework
 *
 * Site: https://www.entdiy.com, E-Mail: xautlx@hotmail.com
 *
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
package com.entdiy.common.web.anna;

import com.entdiy.common.constant.BooleanEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于注解类或属性的元数据，这些元数据可用于代码生成或运行时动态内容生成
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE})
public @interface MetaData {

    /**
     * 简要注解说明：一般对应表单项Label属性显示
     */
    String value();

    /**
     * 提示信息：一般对应表单项的提示说明，支持以HTML格式
     */
    String tooltips() default "";

    /**
     * 注释说明：用于描述代码内部用法说明，一般不用于前端UI显示
     */
    String comments() default "";


    /**
     * 标识属性是否在index页面生成列表项
     */
    boolean listable() default true;

    /**
     * 标识属性是否在index页面生成列表项但是默认为hidden不显示
     */
    boolean listHidden() default false;

    /**
     * 标识属性是否在代码生成项可编辑
     */
    BooleanEnum editable();

    /**
     * 标识属性是否生成表达校验表达式
     */
    boolean validate() default true;


    /**
     * 对于String类型的属性，追加标识按照image图像处理数据
     */
    boolean image() default false;

    /**
     * 对于String类型的属性，追加标识按照image图像处理数据，标识是单图模式还是多图模式
     */
    boolean multiple() default true;

    /**
     * 标识此属性数据源为数据字典，对应数据字典的key值
     */
    String dataDictKey() default "";
}
