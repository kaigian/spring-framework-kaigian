package indi.kaigian.springframework.beans.factory;

import indi.kaigian.springframework.beans.aware.Aware;
import indi.kaigian.springframework.beans.aware.BeanClassLoaderAware;
import indi.kaigian.springframework.beans.aware.BeanFactoryAware;
import indi.kaigian.springframework.beans.aware.BeanNameAware;
import indi.kaigian.springframework.beans.definition.BeanDefinition;
import indi.kaigian.springframework.beans.definition.RootBeanDefinition;
import indi.kaigian.springframework.beans.entity.NullBean;
import indi.kaigian.springframework.beans.entity.PropertyValues;
import indi.kaigian.springframework.beans.support.BeanPostProcessor;
import indi.kaigian.springframework.beans.support.InitializingBean;
import indi.kaigian.springframework.beans.support.InstantiationAwareBeanPostProcessor;
import indi.kaigian.springframework.beans.support.MergedBeanDefinitionPostProcessor;
import indi.kaigian.springframework.core.exception.SpringException;

/**
 * @author kaigian
 **/
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    @Override
    protected Object createBean(String beanName, RootBeanDefinition rootBeanDefinition, Object[] args) throws SpringException {
        // 如果没加载Class，先加载Class
        Class<?> beanClass = rootBeanDefinition.getBeanClass();

        // 实例化前
        Object bean = resolveBeforeInstantiation(beanName, rootBeanDefinition);
        if (bean != null) {
            return bean;
        }

        // 实例化
        try {
            bean = beanClass.newInstance();
        } catch (Exception e) {
            return null;
        }

        // MergedBeanDefinitionPostProcessor
        applyMergedBeanDefinitionPostProcessors(rootBeanDefinition, rootBeanDefinition.getBeanClass(), beanName);

        // 属性填充：包含实例化后
        populateBean(beanName, rootBeanDefinition, bean);

        // 初始化Bean，包括Aware回调、初始化前、初始化、初始化后
        bean = initializeBean(beanName, bean, rootBeanDefinition);

        // 注册销毁逻辑

        return bean;
    }

    protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition rbd) {
        Object bean = null;
        if (hasInstantiationAwareBeanPostProcessors()) {
            Class<?> targetType = rbd.getBeanClass();
            bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
            if (bean != null) {
                bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
            }
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> targetType, String beanName) {
        for (InstantiationAwareBeanPostProcessor bp : getBeanPostProcessorCache().instantiationAware) {
            Object bean = bp.postProcessBeforeInstantiation(targetType, beanName);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    protected void populateBean(String beanName, RootBeanDefinition rbd, Object bean) {
        // 先执行InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation()，如果返回false则不进行属性填充
        for (InstantiationAwareBeanPostProcessor bp : getBeanPostProcessorCache().instantiationAware) {
            if (!bp.postProcessAfterInstantiation(bean, beanName)) {
                return;
            }
        }

        PropertyValues pvs = (rbd.hasPropertyValues() ? rbd.getPropertyValues() : null);

        // 调用InstantiationAwareBeanPostProcessor.postProcessProperties()方法
        // @Autowired和@Resource就是在这一步进行解析的
        for (InstantiationAwareBeanPostProcessor bp : getBeanPostProcessorCache().instantiationAware) {
            pvs = bp.postProcessProperties(pvs, bean, beanName);
        }

        // 设置属性
        if (pvs != null) {
            applyPropertyValues(beanName, rbd, bean, pvs);
        }
    }

    protected void applyMergedBeanDefinitionPostProcessors(RootBeanDefinition rbd, Class<?> beanType, String beanName) {
        for (MergedBeanDefinitionPostProcessor bp : getBeanPostProcessorCache().mergedDefinition) {
            bp.postProcessMergedBeanDefinition(rbd, beanType, beanName);
        }
    }

    protected Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor bp : getBeanPostProcessors()) {
            Object current = bp.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    protected void applyPropertyValues(String beanName, BeanDefinition mbd, Object bw, PropertyValues pvs) {

    }

    protected Object initializeBean(String beanName, Object bean, RootBeanDefinition rbd) {
        // Aware回调
        invokeAwareMethods(beanName, bean);

        // 初始化前
        if (rbd != null) {
            for (BeanPostProcessor bp : getBeanPostProcessors()) {
                bean = bp.postProcessBeforeInitialization(bean, beanName);
            }
        }

        // 初始化
        invokeInitMethods(beanName, bean, rbd);

        // 初始化后
        if (rbd != null) {
            for (BeanPostProcessor bp : getBeanPostProcessors()) {
                bean = bp.postProcessAfterInitialization(bean, beanName);
            }
        }

        return bean;
    }

    private void invokeAwareMethods(String beanName, Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ClassLoader classLoader = getBeanClassLoader();
                if (classLoader != null) {
                    ((BeanClassLoaderAware) bean).setBeanClassLoader(classLoader);
                }
            }
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
        }
    }

    protected void invokeInitMethods(String beanName, Object bean, RootBeanDefinition rbd) {
        // 调用InitializingBean.afterPropertiesSet()
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 执行初始化方法
        if (rbd != null && !(bean instanceof NullBean)) {
            String initMethodName = rbd.getInitMethodName();
        }
    }
}
