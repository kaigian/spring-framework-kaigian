package indi.kaigian.springframework.beans.registry;

import indi.kaigian.springframework.beans.definition.BeanDefinition;

/**
 * 对BeanDefinition的操作
 *
 * @author kaigian
 **/
public interface BeanDefinitionRegistry {

    /**
     * 将beanDefinition注册为一个bean
     *
     * @param beanName       bean名称
     * @param beanDefinition beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 从当前的registry中移除名称beanName的BeanDefinition
     *
     * @param beanName bean名称
     */
    void removeBeanDefinition(String beanName);

    /**
     * 获取名称beanName的BeanDefinition
     *
     * @param beanName bean名称
     * @return 名为beanName的BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 判断当前registry是否包含名称beanName的BeanDefinition
     *
     * @param beanName bean名称
     * @return 是否包含
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取当前registry中的所有BeanDefinition名称列表
     *
     * @return BeanDefinition名称列表
     */
    String[] getBeanDefinitionNames();

    /**
     * 获取当前registry中的所有BeanDefinition数量
     *
     * @return BeanDefinition数量
     */
    int getBeanDefinitionCount();

    /**
     * 判断一个beanName是否存在：是主名或者别名都可以
     *
     * @param beanName bean名称
     * @return 名为beanName的BeanDefinition是否存在
     */
    boolean isBeanNameInUse(String beanName);
}
