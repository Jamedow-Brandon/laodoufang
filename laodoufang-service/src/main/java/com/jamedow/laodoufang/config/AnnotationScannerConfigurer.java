package com.jamedow.laodoufang.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Lazy(true)
class AnnotationScannerConfigurer implements BeanDefinitionRegistryPostProcessor {


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory postProcessBeanFactory) throws BeansException {
        Map<String, Object> map = postProcessBeanFactory.getBeansWithAnnotation(ConsumerMethods.class);
        for (String key : map.keySet()) {
            //System.out.println("beanName= "+ key );
        }
    }
}
