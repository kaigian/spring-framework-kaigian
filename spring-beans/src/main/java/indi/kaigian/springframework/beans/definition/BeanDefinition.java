package indi.kaigian.springframework.beans.definition;

import indi.kaigian.springframework.beans.entity.MutablePropertyValues;

/**
 * BeanDefinition接口，被注释的是源码中未使用的部分
 *
 * @author kaigian
 **/
public interface BeanDefinition {

    /**
     * 单例bean类型
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * 多例bean类型
     */
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 设置父BeanDefinition
     *
     * @param parentName 父BeanDefinition名称
     */
    void setParentName(String parentName);

    /**
     * 获取父BeanDefinition名称，没有则返回null
     *
     * @return 父BeanDefinition名称
     */
    String getParentName();

    /**
     * 设置该bean所对应的Class对象名称
     *
     * @param beanClassName 对应的Class对象名称
     */
    void setBeanClassName(String beanClassName);

    /**
     * 获取对应的Class对象名称
     *
     * @return 对应的Class对象名称
     */
    String getBeanClassName();

    /**
     * 设置bean的类型：单例、多例
     *
     * @param scope bean类型
     */
    void setScope(String scope);

    /**
     * 获取bean类型
     *
     * @return bean类型
     */
    String getScope();

    /**
     * 设置bean是否懒加载
     *
     * @param lazyInit 是否懒加载
     */
    void setLazyInit(boolean lazyInit);

    /**
     * 判断该bean是否懒加载
     *
     * @return 该bean是否懒加载
     */
    boolean isLazyInit();

    /**
     * 设置该bean所依赖的其他bean
     *
     * @param dependsOn 所依赖的bean名称列表
     */
    void setDependsOn(String... dependsOn);

    /**
     * 获取该bean所依赖的bean名称列表
     *
     * @return 所依赖的bean名称列表
     */
    String[] getDependsOn();

    /**
     * 设置bean初始化使用的方法名称
     *
     * @param initMethodName 初始化方法名称
     */
    void setInitMethodName(String initMethodName);

    /**
     * 获取bean初始化使用的方法名称
     *
     * @return 初始化使用的方法名称
     */
    String getInitMethodName();

    /**
     * 设置bean销毁时使用的方法名称
     *
     * @param destroyMethodName 销毁方法名称
     */
    void setDestroyMethodName(String destroyMethodName);

    /**
     * 获取bean销毁时使用的方法名称
     *
     * @return 销毁时使用的方法名称
     */
    String getDestroyMethodName();

    /**
     * 设置BeanDefinition描述
     *
     * @param description 描述
     */
    void setDescription(String description);

    /**
     * 获取BeanDefinition描述
     *
     * @return 描述
     */
    String getDescription();

    /**
     * 是否是单例bean
     *
     * @return 是否是单例bean
     */
    boolean isSingleton();

    /**
     * 是否是多例bean
     *
     * @return 是否是多例bean
     */
    boolean isPrototype();

    /**
     * 是否是抽象的BeanDefinition
     *
     * @return 是否是抽象的BeanDefinition
     */
    boolean isAbstract();

    /**
     * 获取bean对象的属性名称对象映射集合
     *
     * @return 属性名称对象映射集合
     */
    MutablePropertyValues getPropertyValues();

    /**
     * 判断bean对象是否有属性名称对象映射集合
     *
     * @return 是否有属性名称对象映射集合
     */
    default boolean hasPropertyValues() {
        return !getPropertyValues().isEmpty();
    }
}
