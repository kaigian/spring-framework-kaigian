package indi.kaigian.springframework.beans.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对FactoryBean的支持
 *
 * @author kaigian
 **/
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * FactoryBean所创建的单例Bean缓存池
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(16);
}
