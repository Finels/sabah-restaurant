package com.fasteam.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Description:  用于获取spring上下文的工具类
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context = null;

    public SpringContextUtil() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static String getMessage(String key) {
        return context.getMessage(key, (Object[]) null, Locale.getDefault());
    }

    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }
}
