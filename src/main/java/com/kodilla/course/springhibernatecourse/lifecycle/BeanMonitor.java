package com.kodilla.course.springhibernatecourse.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanMonitor implements BeanPostProcessor {

    //Metody te pozwalają na zmodyfikowanie beana w trakcie jego tworzenia – odpowiednio przed i po inicjalizacji.
    // Można ten mechanizm wykorzystać do globalnej zmiany zachowania niektórych beanów – pisząc kod tylko w tym jednym miejscu.
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws
            BeansException {

        System.out.println("Before initialization of bean: " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("After initialziation of bean: " + beanName);
        return bean;
    }

}
