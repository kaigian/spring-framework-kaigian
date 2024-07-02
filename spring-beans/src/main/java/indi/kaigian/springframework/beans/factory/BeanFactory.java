package indi.kaigian.springframework.beans.factory;

import indi.kaigian.springframework.core.exception.SpringErrorCodeEnum;
import indi.kaigian.springframework.core.exception.SpringException;

/**
 * @author kaigian
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
     */
    default Object getBean(String name) {
        throw new SpringException(SpringErrorCodeEnum.SERVICE_NOT_SUPPORT);
    }

    /**
     * 根据名字获取一个requiredType类型的bean
     *
     * @param name         bean名称
     * @param requiredType 需要的bean类型
     * @param <T>          泛型
     * @return 符合条件的bean
     */
    default <T> T getBean(String name, Class<T> requiredType) {
        throw new SpringException(SpringErrorCodeEnum.SERVICE_NOT_SUPPORT);
    }

    /**
     * 根据名字和参数获取一个bean
     *
     * @param name bean名称
     * @param args 参数
     * @return 符合条件的bean
     */
    default Object getBean(String name, Object... args) {
        throw new SpringException(SpringErrorCodeEnum.SERVICE_NOT_SUPPORT);
    }

    /**
     * 根据类型获取一个bean
     *
     * @param requiredType 需要的bean类型
     * @param <T>          泛型
     * @return 符合条件的bean
     */
    default <T> T getBean(Class<T> requiredType) {
        throw new SpringException(SpringErrorCodeEnum.SERVICE_NOT_SUPPORT);
    }

    /**
     * 根据参数和类型获取一个bean
     *
     * @param requiredType 需要的bean类型
     * @param args         参数
     * @param <T>          泛型
     * @return 符合条件的bean
     */
    default <T> T getBean(Class<T> requiredType, Object... args) {
        throw new SpringException(SpringErrorCodeEnum.SERVICE_NOT_SUPPORT);
    }

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
     */
    boolean isSingleton(String name);

    /**
     * 名为name的bean是否为多例
     *
     * @param name bean名称
     * @return 名为name的bean是否为多例
     */
    boolean isPrototype(String name);
}
