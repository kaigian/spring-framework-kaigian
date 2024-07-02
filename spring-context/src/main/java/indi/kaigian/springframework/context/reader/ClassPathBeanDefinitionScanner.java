package indi.kaigian.springframework.context.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import indi.kaigian.springframework.beans.definition.BeanDefinition;
import indi.kaigian.springframework.beans.definition.BeanDefinitionHolder;
import indi.kaigian.springframework.beans.definition.GenericBeanDefinition;
import indi.kaigian.springframework.beans.annotation.Component;
import indi.kaigian.springframework.beans.annotation.Lazy;
import indi.kaigian.springframework.beans.annotation.Scope;
import indi.kaigian.springframework.beans.registry.BeanDefinitionRegistry;
import indi.kaigian.springframework.core.utils.ClassUtils;
import indi.kaigian.springframework.core.utils.StringUtils;

import java.beans.Introspector;
import java.io.File;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author kaigian
 **/
public class ClassPathBeanDefinitionScanner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BeanDefinitionRegistry bdRegistry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry bdRegistry) {
        this.bdRegistry = bdRegistry;
    }

    public void scan(String... scanPaths) {
        logger.info("BeaDefinition扫描开始");
        for (String scanPath : scanPaths) {
            logger.info("开始扫描{}", scanPath);
            doScan(scanPath);
        }
    }

    private void doScan(String scanPath) {
        Set<BeanDefinition> bdSet = findBeanDefinition(scanPath);

        for (BeanDefinition bd : bdSet) {
            GenericBeanDefinition gbd = (GenericBeanDefinition) bd;
            Class<?> beanClass = gbd.getBeanClass();

            // 解析Scope
            if (beanClass.isAssignableFrom(Scope.class)) {
                gbd.setScope(beanClass.getAnnotation(Scope.class).value());
            } else {
                gbd.setScope(BeanDefinition.SCOPE_SINGLETON);
            }

            // 生成beanName
            String beanName;
            String nameVal = beanClass.getAnnotation(Component.class).value();
            if (StringUtils.isNotEmpty(nameVal)) {
                beanName = nameVal;
            } else {
                beanName = Introspector.decapitalize(beanClass.getSimpleName());
            }

            // 给注解赋初值
            gbd.setLazyInit(false);

            // 解析@Lazy、@Primary、@DependsOn、@Role、@Description
            if (beanClass.isAssignableFrom(Lazy.class)) {
                gbd.setLazyInit(true);
            }

            BeanDefinitionHolder bdHolder = new BeanDefinitionHolder(gbd, beanName);
            registerBeanDefinition(bdHolder);
        }

    }

    /**
     * 读取scanPath下的所有文件
     *
     * @param scanPath 扫描路径
     * @return 扫描路径下的所有问题
     */
    private File[] loadScanFiles(String scanPath) {
        scanPath = scanPath.replace(".", "/");
        // TODO: 2022/1/12 Spring使用使用的是ResourcePatternResolver
        ClassLoader classLoader = ClassUtils.getSystemClassLoader();
        URL resource = classLoader.getResource(scanPath);
        if (resource == null) {
            return null;
        }
        File scanDir = new File(resource.getFile());
        if (!scanDir.isDirectory()) {
            return null;
        }
        File[] scanFiles = scanDir.listFiles();
        if (scanFiles == null || scanFiles.length == 0) {
            return null;
        }
        return scanFiles;
    }

    /**
     * 扫描beanDefinition
     *
     * @param scanPath 扫描路径
     * @return 所有扫描路径下的beanDefinition
     */
    private Set<BeanDefinition> findBeanDefinition(String scanPath) {
        Set<BeanDefinition> bdSet = new LinkedHashSet<>();

        // 加载扫描路径下的所有文件
        File[] scanFiles = loadScanFiles(scanPath);
        ClassLoader classLoader = ClassUtils.getSystemClassLoader();
        if (scanFiles != null) {
            for (File scanFile : scanFiles) {
                String absolutePath = scanFile.getAbsolutePath();
                if (!absolutePath.endsWith(".class")) {
                    continue;
                }
                String loadPath = absolutePath.substring(absolutePath.indexOf("target\\classes\\") + 15, absolutePath.length() - 6);
                loadPath = loadPath.replace("\\", ".");
                Class<?> clazz = null;
                try {
                    clazz = classLoader.loadClass(loadPath);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (clazz != null && clazz.isAnnotationPresent(Component.class)) {
                    GenericBeanDefinition bd = new GenericBeanDefinition();
                    bd.setBeanClass(clazz);
                    bdSet.add(bd);
                }
            }
        }
        return bdSet;
    }

    public void registerBeanDefinition(BeanDefinitionHolder bdHolder) {
        bdRegistry.registerBeanDefinition(bdHolder.getBeanName(), bdHolder.getBeanDefinition());
    }
}
