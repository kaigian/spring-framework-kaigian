package indi.kaigian.springframework.beans.aware;

/**
 * @author kaigian
 **/
public interface BeanClassLoaderAware extends Aware {

    /**
     * 设置该bean的类加载器
     *
     * @param classLoader 类加载器
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
