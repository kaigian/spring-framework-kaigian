package indi.kaigian.springframework.beans.registry;

/**
 * 单例Bean注册接口
 *
 * @author kaigian
 **/
public interface SingletonBeanRegistry {

    /**
     * 将singletonObject注册为名为beanName的单例bean
     *
     * @param beanName        bean名称
     * @param singletonObject bean对象
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取名为beanName的单例bean
     *
     * @param beanName bean名称
     * @return 单例bean
     */
    Object getSingleton(String beanName);

    /**
     * 是否包含名为beanName的单例bean
     *
     * @param beanName bean名称
     * @return 是否包含
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取所有单例Bean的名称
     *
     * @return 所有单例Bean的名称
     */
    String[] getSingletonNames();

    /**
     * 获取单例Bean的数量
     *
     * @return 单例Bean的数量
     */
    int getSingletonCount();

    /**
     * 获取注册器互斥锁
     *
     * @return 互斥锁
     */
    Object getSingletonMutex();
}
