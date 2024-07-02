package indi.kaigian.springframework.beans.factory;

/**
 * 表示BeanFactory拥有继承的能力（可以有父BeanFactory)
 *
 * @author kaigian
 **/
public interface HierarchicalBeanFactory extends BeanFactory {

    /**
     * 获取父BeanFactory
     *
     * @return 父BeanFactory
     */
    BeanFactory getParentBeanFactory();

    /**
     * 查看是否当前的BeanFactory中有名称name的Bean，不查找父BeanFactory
     *
     * @param name bean名称
     * @return 当前BeanFactory是否有bean
     */
    boolean containsLocalBean(String name);
}
