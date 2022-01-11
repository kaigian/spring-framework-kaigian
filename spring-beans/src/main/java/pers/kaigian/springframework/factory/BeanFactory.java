package pers.kaigian.springframework.factory;

/**
 * @author BrianHu
 * @create 2022-01-11 11:35
 **/
public interface BeanFactory {

    /**
     * FactoryBean区别的前缀
     */
    String FACTORY_BEAN_PREFIX = "&";

    /**
     * 根据名字获取一个bean
     *
     * @param name bean名称
     * @return 符合条件的bean
     * @throws Throwable 抛出的异常或错误信息
     */
    Object getBean(String name) throws Throwable;

    /**
     * 根据名字获取一个requiredType类型的bean
     *
     * @param name         bean名称
     * @param requiredType 需要的bean类型
     * @param <T>          泛型
     * @return 符合条件的bean
     * @throws Throwable 抛出的异常或错误信息
     */
    <T> T getBean(String name, Class<T> requiredType) throws Throwable;

    /**
     * 根据名字和参数获取一个bean
     *
     * @param name bean名称
     * @param args 参数
     * @return 符合条件的bean
     * @throws Throwable 抛出的异常或错误信息
     */
    Object getBean(String name, Object... args) throws Throwable;

    /**
     * 根据类型获取一个bean
     *
     * @param requiredType 需要的bean类型
     * @param <T>          泛型
     * @return 符合条件的bean
     * @throws Throwable 抛出的异常或错误信息
     */
    <T> T getBean(Class<T> requiredType) throws Throwable;

    /**
     * 根据参数和类型获取一个bean
     *
     * @param requiredType 需要的bean类型
     * @param args         参数
     * @param <T>          泛型
     * @return 符合条件的bean
     * @throws Throwable 抛出的异常或错误信息
     */
    <T> T getBean(Class<T> requiredType, Object... args) throws Throwable;

    /**
     * 是否有名为name的bean
     *
     * @param name bean名称
     * @return 是否有名为name的bean
     */
    boolean containsBean(String name);

    /**
     * 名为name的bean是否为单例
     *
     * @param name bean名称
     * @return 名为name的bean是否为单例
     * @throws Throwable 抛出的异常或错误信息
     */
    boolean isSingleton(String name) throws Throwable;

    /**
     * 名为name的bean是否为多例
     *
     * @param name bean名称
     * @return 名为name的bean是否为多例
     * @throws Throwable 抛出的异常或错误信息
     */
    boolean isPrototype(String name) throws Throwable;
}
