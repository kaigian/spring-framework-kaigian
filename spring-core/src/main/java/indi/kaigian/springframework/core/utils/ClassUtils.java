package indi.kaigian.springframework.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kaigian
 **/
public class ClassUtils {

    private static final Map<String, Class<?>> classMap = new HashMap<>(256);

    public static Class<?> forName(String className, ClassLoader classLoader) throws ClassNotFoundException {
        if (classMap.containsKey(className)) {
            return classMap.get(className);
        }
        if (classLoader == null) {
            classLoader = getSystemClassLoader();
        }
        Class<?> loadClass = classLoader.loadClass(className);
        classMap.put(className, loadClass);
        return loadClass;
    }

    public static ClassLoader getSystemClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }
}
