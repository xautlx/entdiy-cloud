package com.entdiy.common.util;

import com.entdiy.common.exception.Validation;
import com.entdiy.common.web.AppContextHolder;

import java.time.LocalDateTime;

public class DateUtil {

    private static LocalDateTime currentDateTime;

    public static void setCurrentDateTime(LocalDateTime localDateTime) {
        Validation.isTrue(AppContextHolder.isDevMode() || AppContextHolder.isDemoMode(), "当前操作只能在开发演示运行模式才可用");
        if (localDateTime == null) {
            currentDateTime = null;
        } else {
            currentDateTime = localDateTime;
        }
    }

    /**
     * 为了便于在模拟数据程序中控制业务数据获取到的当前时间
     * 提供一个帮助类处理当前时间，为了避免误操作，只有在devMode开发模式才允许“篡改”当前时间
     *
     * @return
     */
    public static LocalDateTime currentDateTime() {
        if (currentDateTime == null) {
            return LocalDateTime.now();
        }
        if (AppContextHolder.isDevMode() || AppContextHolder.isDemoMode()) {
            return currentDateTime;
        } else {
            return LocalDateTime.now();
        }
    }
}
