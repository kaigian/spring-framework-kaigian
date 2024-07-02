package indi.kaigian.springframework.beans.aware;

import indi.kaigian.springframework.beans.factory.BeanFactory;
import indi.kaigian.springframework.core.exception.SpringException;

/**
 * @author kaigian
 **/
public interface BeanFactoryAware extends Aware {

    /**
     * 设置生产这个bean的BeanFactory
     *
     * @param beanFactory beanFactory
     * @throws SpringException 异常
     */
    void setBeanFactory(BeanFactory beanFactory) throws SpringException;
}
