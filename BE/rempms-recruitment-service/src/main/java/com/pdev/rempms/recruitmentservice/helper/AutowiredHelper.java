package com.pdev.rempms.recruitmentservice.helper;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

// TODO need to deep learn about this class implementations
@Component
public class AutowiredHelper implements ApplicationContextAware {

    private static AutowiredHelper INSTANCE = new AutowiredHelper();
    private static ApplicationContext applicationContext;

    private AutowiredHelper() {

    }

    public static void autowire(Object classToAutowire, Object... beansToAutowireInClass) {
        for (Object bean : beansToAutowireInClass) {
            if (bean == null) {
                applicationContext.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
            }
        }
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        AutowiredHelper.applicationContext = applicationContext;
    }

    public static AutowiredHelper getINSTANCE() {
        return INSTANCE;
    }
}
