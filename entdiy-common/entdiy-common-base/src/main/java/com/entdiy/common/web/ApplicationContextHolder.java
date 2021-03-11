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

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Slf4j
@EnableConfigurationProperties(AppConfigProperties.class)
public class ApplicationContextHolder implements ApplicationContextAware {

    @Autowired
    private AppConfigProperties appConfigPropertiesInstance;

    private static AppConfigProperties appConfigProperties;

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
        if (appConfigPropertiesInstance.isDevMode()) {
            log.info("Running at DEV mode");
            // 开发模式，把版本信息设置为当前时间戳以触发JS、CSS等资源刷新加载
            appConfigPropertiesInstance.setBuildVersion("DEV_" + System.currentTimeMillis());
        }
        ApplicationContextHolder.appConfigProperties = appConfigPropertiesInstance;
    }

    public static boolean isDemoMode() {
        return appConfigProperties.isDemoMode();
    }

    public static boolean isDevMode() {
        return appConfigProperties.isDevMode();
    }

    public static String getBuildVersion() {
        return appConfigProperties.getBuildVersion();
    }

    public static String getSystemName() {
        return appConfigProperties.getSystemName();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
