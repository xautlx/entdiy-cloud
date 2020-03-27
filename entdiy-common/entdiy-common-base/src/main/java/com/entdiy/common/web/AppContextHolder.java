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
package com.entdiy.common.web;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppContextHolder {

    @Setter
    private static AppContextProperties appContextProperties;

    public static boolean isDemoMode() {
        return appContextProperties.isDemoMode();
    }

    public static boolean isDevMode() {
        return appContextProperties.isDevMode();
    }

    public static boolean isProductionMode() {
        return !isDevMode() && !isDemoMode();
    }

    public static String getBuildVersion() {
        if (isDevMode()) {
            // 开发模式，把版本信息设置为当前时间戳以触发JS、CSS等资源刷新加载
            return "DEV_" + System.currentTimeMillis();
        } else {
            return appContextProperties.getBuildVersion();
        }
    }

    public static String getSystemName() {
        return appContextProperties.getSystemName();
    }
}
