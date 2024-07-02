package indi.kaigian.springframework.context;

import indi.kaigian.springframework.beans.definition.BeanDefinition;
import indi.kaigian.springframework.beans.factory.BeanFactory;
import indi.kaigian.springframework.beans.factory.DefaultListableBeanFactory;
import indi.kaigian.springframework.beans.registry.BeanDefinitionRegistry;

/**
 * @author kaigian
 **/
public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {

    private final DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public int getBeanDefinitionCount() {
        return 0;
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        return false;
    }

    @Override
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
