package indi.kaigian.springframework.context.reader;

import indi.kaigian.springframework.beans.registry.BeanDefinitionRegistry;

/**
 * @author kaigian
 **/
public class AnnotatedBeanDefinitionReader {

    private final BeanDefinitionRegistry bdRegistry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry bdRegistry) {
        this.bdRegistry = bdRegistry;
    }

    public void register(Class<?>... componentClasses) {
        for (Class<?> clazz : componentClasses) {
            registerBean(clazz);
        }
    }

    public void registerBean(Class<?> beanClass) {

    }
}
