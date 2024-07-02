package indi.kaigian.springframework.beans.definition;

import indi.kaigian.springframework.beans.entity.MutablePropertyValues;
import indi.kaigian.springframework.core.exception.SpringErrorCodeEnum;
import indi.kaigian.springframework.core.exception.SpringException;
import indi.kaigian.springframework.core.utils.ClassUtils;

/**
 * @author kaigian
 **/
public abstract class AbstractBeanDefinition implements BeanDefinition, Cloneable {

    public static final String SCOPE_DEFAULT = "";

    /**
     * bean类型，如果类被加载则是Class对象，如果没有则是String类型的类路径
     */
    private Object beanClass;

    /**
     * bean类型，单例、多例
     */
    private String scope = SCOPE_DEFAULT;

    /**
     * 是否懒加载
     */
    private boolean lazyInit;

    /**
     * 依赖的bean名称列表
     */
    private String[] dependsOn;

    /**
     * 初始化方法
     */
    private String initMethodName;

    /**
     * 销毁方法
     */
    private String destroyMethodName;

    /**
     * 描述
     */
    private String description;

    /**
     * 属性名称对象映射
     */
    private MutablePropertyValues propertyValues;

    @Override
    public void setBeanClassName(String beanClassName) {
        this.beanClass = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        Object beanClassObj = this.beanClass;
        if (beanClassObj instanceof Class<?>) {
            return ((Class<?>) beanClassObj).getName();
        }
        return (String) beanClassObj;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?> getBeanClass() {
        Object beanClassObj = this.beanClass;
        if (beanClassObj == null) {
            throw new SpringException(SpringErrorCodeEnum.ERROR_CODE);
        }
        if (!(beanClassObj instanceof Class<?>)) {
            throw new SpringException(SpringErrorCodeEnum.ERROR_CODE);
        }
        return (Class<?>) beanClassObj;
    }

    public boolean hasBeanClass() {
        return (this.beanClass instanceof Class);
    }

    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        String className = getBeanClassName();
        if (className == null) {
            return null;
        }
        Class<?> resolvedClass = ClassUtils.forName(className, classLoader);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Override
    public boolean isLazyInit() {
        return this.lazyInit;
    }

    @Override
    public void setDependsOn(String... dependsOn) {
        this.dependsOn = dependsOn;
    }

    @Override
    public String[] getDependsOn() {
        return this.dependsOn;
    }

    @Override
    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    @Override
    public String getInitMethodName() {
        return this.initMethodName;
    }

    @Override
    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public String getDestroyMethodName() {
        return this.destroyMethodName;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean isSingleton() {
        return BeanDefinition.SCOPE_SINGLETON.equals(this.scope);
    }

    @Override
    public boolean isPrototype() {
        return BeanDefinition.SCOPE_PROTOTYPE.equals(this.scope);
    }

    @Override
    public boolean isAbstract() {
        return false;
    }

    public void setPropertyValues(MutablePropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public MutablePropertyValues getPropertyValues() {
        if (this.propertyValues == null) {
            this.propertyValues = new MutablePropertyValues();
        }
        return this.propertyValues;
    }

    @Override
    public boolean hasPropertyValues() {
        return (this.propertyValues != null && !this.propertyValues.isEmpty());
    }
}
