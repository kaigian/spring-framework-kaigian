package indi.kaigian.springframework.beans.registry;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kaigian
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 单例Bean缓存池
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * 已经注册的bean的名称集合
     */
    private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (singletonObjects) {
            singletonObjects.put(beanName, singletonObject);
            registeredSingletons.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletonObjects.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        synchronized (singletonObjects) {
            return registeredSingletons.toArray(new String[0]);
        }
    }

    @Override
    public int getSingletonCount() {
        synchronized (singletonObjects) {
            return registeredSingletons.size();
        }
    }

    @Override
    public Object getSingletonMutex() {
        return singletonObjects;
    }
}
