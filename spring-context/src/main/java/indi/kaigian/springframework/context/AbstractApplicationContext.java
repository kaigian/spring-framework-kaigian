package indi.kaigian.springframework.context;

import indi.kaigian.springframework.beans.factory.BeanFactory;

/**
 * @author kaigian
 **/
public abstract class AbstractApplicationContext implements ApplicationContext {

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    /**
     * 获取当前使用的BeanFactory，具体交给子类实现
     *
     * @return 当前使用的BeanFactory
     */
    @Override
    public abstract BeanFactory getBeanFactory();
}
