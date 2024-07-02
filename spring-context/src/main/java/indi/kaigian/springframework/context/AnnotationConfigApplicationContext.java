package indi.kaigian.springframework.context;

import indi.kaigian.springframework.context.annotation.ComponentScan;
import indi.kaigian.springframework.context.reader.AnnotatedBeanDefinitionReader;
import indi.kaigian.springframework.context.reader.ClassPathBeanDefinitionScanner;
import indi.kaigian.springframework.context.support.AnnotationConfigRegistry;
import indi.kaigian.springframework.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于注解的Spring上下文环境
 *
 * @author kaigian
 **/
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

    private final ClassPathBeanDefinitionScanner scanner;

    private final AnnotatedBeanDefinitionReader reader;

    private final List<String> scanPaths = new ArrayList<>();

    public AnnotationConfigApplicationContext() {
        super();
        this.scanner = new ClassPathBeanDefinitionScanner(this);
        this.reader = new AnnotatedBeanDefinitionReader(this);
    }

    public AnnotationConfigApplicationContext(Class<?>... configClasses) {
        this();
        register(configClasses);
        refresh();
    }

    @Override
    public void register(Class<?>... configClasses) {
        reader.register(configClasses);
        for (Class<?> configClass : configClasses) {
            if (configClass.isAnnotationPresent(ComponentScan.class)) {
                String scanPath = configClass.getAnnotation(ComponentScan.class).value();
                if (StringUtils.isNotEmpty(scanPath)) {
                    scanPaths.add(scanPath);
                }
            }
        }
    }

    @Override
    public void scan(String... basePackages) {
        this.scanner.scan(basePackages);
    }

    public void refresh() {
        String[] paths = scanPaths.toArray(new String[0]);
        scanner.scan(paths);
    }
}
