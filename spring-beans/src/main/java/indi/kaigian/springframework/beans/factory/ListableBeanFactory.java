package indi.kaigian.springframework.beans.factory;

/**
 * 表示BeanFactory可以枚举所有Bean实例，如果有父BeanFactory不会访问父BeanFactory中的Bean
 *
 * @author kaigian
 **/
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 是否包含名为beanName的BeanDefinition
     *
     * @param beanName bean名称
     * @return 是否包含
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取当前BeanFactory中的BeanDefinition数量
     *
     * @return BeanDefinition数量
     */
    int getBeanDefinitionCount();

    /**
     * 获取当前BeanFactory所定义的所有Bean名称
     *
     * @return 所有Bean名称
     */
    String[] getBeanDefinitionNames();
}
