package ru.utelksp.upo.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Created by ZotovES on 07.04.2019
 * Сервис для внедрения зависимостей в энтити листнер
 */
@Component
public class AutowireHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private AutowireHelper() {
    }

    public static void autowire(Object classToAutowire) {
        AutowireHelper.applicationContext.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        AutowireHelper.applicationContext = applicationContext;
    }
}