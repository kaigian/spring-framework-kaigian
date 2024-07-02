package indi.kaigian.springframework.beans.support;

import indi.kaigian.springframework.beans.definition.RootBeanDefinition;

/**
 * @author kaigian
 **/
public interface MergedBeanDefinitionPostProcessor extends BeanPostProcessor {

    /**
     * @param beanDefinition
     * @param beanType
     * @param beanName
     */
    void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName);

    /**
     * @param beanName
     */
    default void resetBeanDefinition(String beanName) {
    }
}
