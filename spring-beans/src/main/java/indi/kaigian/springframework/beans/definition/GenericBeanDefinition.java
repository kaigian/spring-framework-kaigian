package indi.kaigian.springframework.beans.definition;

/**
 * @author kaigian
 **/
public class GenericBeanDefinition extends AbstractBeanDefinition {

    /**
     * 父BeanDefinition名称
     */
    private String parentName;

    public GenericBeanDefinition() {
        super();
    }

    @Override
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String getParentName() {
        return parentName;
    }
}
