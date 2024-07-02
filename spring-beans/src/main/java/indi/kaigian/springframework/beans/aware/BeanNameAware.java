package indi.kaigian.springframework.beans.aware;

/**
 * @author kaigian
 **/
public interface BeanNameAware extends Aware {
    /**
     * 设置bean名称
     *
     * @param name bean名称
     */
    void setBeanName(String name);
}
