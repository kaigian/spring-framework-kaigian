package indi.kaigian.springframework.beans.support;

import indi.kaigian.springframework.core.exception.SpringException;

/**
 * bean销毁时的扩展点
 *
 * @author kaigian
 **/
public interface DestructionAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 销毁前逻辑
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @throws SpringException 异常
     */
    void postProcessBeforeDestruction(Object bean, String beanName) throws SpringException;

    /**
     * 判断该bean是否需要被销毁
     *
     * @param bean bean对象
     * @return 是否需要被销毁
     */
    default boolean requiresDestruction(Object bean) {
        return true;
    }

}
