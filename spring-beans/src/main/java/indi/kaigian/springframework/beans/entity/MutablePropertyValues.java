package indi.kaigian.springframework.beans.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author kaigian
 **/
public class MutablePropertyValues implements PropertyValues {

    private final List<PropertyValue> propertyValueList;

    private Set<String> processedProperties;

    public MutablePropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    public MutablePropertyValues(List<PropertyValue> propertyValueList) {
        this.propertyValueList = (propertyValueList != null ? propertyValueList : new ArrayList<>());
    }

    public List<PropertyValue> getPropertyValueList() {
        return this.propertyValueList;
    }

    public int size() {
        return this.propertyValueList.size();
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }
}
