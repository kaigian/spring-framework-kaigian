package indi.kaigian.springframework.test;

import indi.kaigian.springframework.context.AnnotationConfigApplicationContext;
import indi.kaigian.springframework.test.service.UserService;

/**
 * @author kaigian
 **/
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) annotationConfigApplicationContext.getBean("userService");
        userService.test();
    }
}
