package indi.kaigian.springframework.beans.factory;

import indi.kaigian.springframework.beans.definition.BeanDefinition;
import indi.kaigian.springframework.beans.definition.GenericBeanDefinition;
import indi.kaigian.springframework.beans.definition.RootBeanDefinition;
import indi.kaigian.springframework.beans.entity.NullBean;
import indi.kaigian.springframework.beans.registry.FactoryBeanRegistrySupport;
import indi.kaigian.springframework.beans.support.*;
import indi.kaigian.springframework.core.exception.SpringErrorCodeEnum;
import indi.kaigian.springframework.core.exception.SpringException;
import indi.kaigian.springframework.core.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kaigian
 **/
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /**
     * BeanPostProcessor缓存
     */
    static class BeanPostProcessorCache {

        final List<InstantiationAwareBeanPostProcessor> instantiationAware = new ArrayList<>();

        final List<SmartInstantiationAwareBeanPostProcessor> smartInstantiationAware = new ArrayList<>();

        final List<DestructionAwareBeanPostProcessor> destructionAware = new ArrayList<>();

        final List<MergedBeanDefinitionPostProcessor> mergedDefinition = new ArrayList<>();
    }

    private BeanFactory parentBeanFactory;

    private ClassLoader beanClassLoader = ClassUtils.getSystemClassLoader();

    /**
     * 特殊BeanPostProcessor分类缓存
     */
    private BeanPostProcessorCache beanPostProcessorCache = new BeanPostProcessorCache();

    /**
     * BeanPostProcessor缓存
     */
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * 合并后的BeanDefinition缓存池
     */
    private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);

    public AbstractBeanFactory() {
    }

    public AbstractBeanFactory(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null, (Object[]) null);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return doGetBean(name, requiredType, (Object[]) null);
    }

    @Override
    public Object getBean(String name, Object... args) {
        return doGetBean(name, null, args);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return doGetBean(null, requiredType, (Object[]) null);
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) {
        return doGetBean(null, requiredType, args);
    }

    private <T> T doGetBean(String name, Class<T> requiredType, Object... args) {
        // 名称转换：
        String beanName = name;
        Object bean = getSingleton(beanName);
        if (bean != null) {
            // TODO: 2022/4/29 判断是否是FactoryBean，这里先直接返回

        } else {
            // 暂不考虑父子BeanFactory

            // 合并BeanDefinition用来创建Bean
            RootBeanDefinition rbd = getMergedLocalBeanDefinition(beanName);

            // TODO: 2022/4/29 处理@DependsOn注解
            // 判断BeanDefinition类型：是否是单例
            if (BeanDefinition.SCOPE_SINGLETON.equals(rbd.getScope())) {
                bean = createBean(beanName, rbd, args);
                // 单例bean放入缓存池
                registerSingleton(beanName, bean);
            }
            // 多例Bean直接创建
            else if (BeanDefinition.SCOPE_PROTOTYPE.equals(rbd.getScope())) {
                bean = createBean(beanName, rbd, args);
            }
            // 其他类型的BeanDefinition：Request、Session等，暂不考虑
            else {
                bean = new NullBean();
            }
        }
        // 检查类型
        return adaptBeanInstance(beanName, bean, requiredType);
    }

    protected RootBeanDefinition getMergedLocalBeanDefinition(String beanName) {
        RootBeanDefinition rbd = new RootBeanDefinition();
        GenericBeanDefinition gbd = (GenericBeanDefinition) getBeanDefinition(beanName);
        rbd.setBeanClass(gbd.getBeanClass());
        rbd.setScope(gbd.getScope());
        return rbd;
    }

    <T> T adaptBeanInstance(String name, Object bean, Class<?> requiredType) {
        // TODO: 2022/5/1 需要进行类型转换 
        if (requiredType != null && !(requiredType.isInstance(bean))) {
            throw new SpringException(SpringErrorCodeEnum.SERVICE_NOT_SUPPORT);
        }
        return (T) bean;
    }

    @Override
    public boolean containsBean(String name) {
        return (containsSingleton(name) || containsBeanDefinition(name));
    }

    @Override
    public boolean isSingleton(String name) {
        if (containsSingleton(name) || containsBeanDefinition(name)) {
            BeanDefinition beanDefinition = getBeanDefinition(name);
            return BeanDefinition.SCOPE_SINGLETON.equals(beanDefinition.getScope());
        }
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        if (containsSingleton(name) || containsBeanDefinition(name)) {
            BeanDefinition beanDefinition = getBeanDefinition(name);
            return BeanDefinition.SCOPE_PROTOTYPE.equals(beanDefinition.getScope());
        }
        return false;
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public BeanPostProcessorCache getBeanPostProcessorCache() {
        BeanPostProcessorCache bpCache = this.beanPostProcessorCache;
        if (bpCache == null) {
            bpCache = new BeanPostProcessorCache();
            for (BeanPostProcessor bp : this.beanPostProcessors) {
                if (bp instanceof InstantiationAwareBeanPostProcessor) {
                    bpCache.instantiationAware.add((InstantiationAwareBeanPostProcessor) bp);
                    if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
                        bpCache.smartInstantiationAware.add((SmartInstantiationAwareBeanPostProcessor) bp);
                    }
                }
                if (bp instanceof DestructionAwareBeanPostProcessor) {
                    bpCache.destructionAware.add((DestructionAwareBeanPostProcessor) bp);
                }
                if (bp instanceof MergedBeanDefinitionPostProcessor) {
                    bpCache.mergedDefinition.add((MergedBeanDefinitionPostProcessor) bp);
                }
            }
            this.beanPostProcessorCache = bpCache;
        }
        return bpCache;
    }

    protected boolean hasInstantiationAwareBeanPostProcessors() {
        return !this.beanPostProcessorCache.instantiationAware.isEmpty();
    }

    @Override
    public void setParentBeanFactory(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return parentBeanFactory;
    }

    @Override
    public boolean containsLocalBean(String name) {
        return false;
    }

    /**
     * 通过原始的BeanDefinition缓存池来判断是否有名为beanName的bean
     * 原始的BeanDefinition缓存池留待子类实现
     *
     * @param beanName bean名称
     * @return 是否含有
     */
    protected abstract boolean containsBeanDefinition(String beanName);

    /**
     * 通过原始的BeanDefinition缓存池获取BeanDefinition
     * 原始的BeanDefinition缓存池留待子类实现
     *
     * @param beanName bean名称
     * @return 原始的BeanDefinition
     * @throws SpringException 异常
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws SpringException;

    /**
     * 创建一个bean，留待子类具体实现
     *
     * @param beanName bean名称
     * @param mbd      经过合并的BeanDefinition
     * @param args     创建bean用到的参数
     * @return 创建出的bean
     * @throws SpringException 异常
     */
    protected abstract Object createBean(String beanName, RootBeanDefinition mbd, Object[] args) throws SpringException;
}
