package indi.kaigian.springframework.core.utils;

/**
 * @author kaigian
 **/
public class StringUtils {

    public static Boolean isEmpty(String string) {
        return (string == null || string.length() == 0);
    }

    public static Boolean isNotEmpty(String string) {
        return (string != null && string.length() > 0);
    }
}
