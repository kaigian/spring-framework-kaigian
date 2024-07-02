package indi.kaigian.springframework.beans.factory;

import indi.kaigian.springframework.beans.definition.BeanDefinition;
import indi.kaigian.springframework.beans.registry.BeanDefinitionRegistry;
import indi.kaigian.springframework.core.exception.SpringException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kaigian
 **/
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    /**
     * beanDefinition缓存池
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 单例bean缓存池
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) {

    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return 0;
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        return false;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws SpringException {
        return beanDefinitionMap.get(beanName);
    }
}
