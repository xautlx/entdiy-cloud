package com.entdiy.common.web.databind;

import com.entdiy.common.constant.BaseConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 全局的参数绑定处理定制
 */
@ControllerAdvice
public class GlobalBindingInitializer {

    @InitBinder
    public void registerCustomEditors(WebDataBinder binder) {

        //自定义类型属性转换，动态处理日期、时间等不同格式数据转换
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StringUtils.isBlank(text)) {
                    // Treat empty String as null value.
                    setValue(null);
                } else {
                    setValue(LocalDate.parse(text, BaseConstant.LocalDateFormatter));
                }
            }

            @Override
            public String getAsText() {
                LocalDate value = (LocalDate) getValue();
                return (value != null ? value.format(BaseConstant.LocalDateFormatter) : "");
            }
        });
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StringUtils.isBlank(text)) {
                    // Treat empty String as null value.
                    setValue(null);
                } else {
                    setValue(LocalDateTime.parse(text, BaseConstant.LocalDateTimeFormatter));
                }
            }

            @Override
            public String getAsText() {
                LocalDateTime value = (LocalDateTime) getValue();
                return (value != null ? value.format(BaseConstant.LocalDateTimeFormatter) : "");
            }
        });

        // Converts empty strings into null when a form is submitted
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

    }
}
