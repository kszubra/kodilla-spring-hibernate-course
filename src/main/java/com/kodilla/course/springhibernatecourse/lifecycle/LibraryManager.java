package com.kodilla.course.springhibernatecourse.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class LibraryManager
        implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean, DisposableBean {

    private String beanName;

    @Override
    public void setBeanName(String name) {
        beanName = name;
        System.out.println("Name of the bean: " + name);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader instance passed");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactory instance passed");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Properties are set");
    }

    @PostConstruct
    public void exampleConstructMethod() {
        System.out.println("Method with @PostConstruct annotation");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Method destroy has been called");
    }

    @PreDestroy
    public void exampleDestroyMethod() {
        System.out.println("Method with @PreDestroy annotation");
    }

}
