package hjy.com.red_book_community.utils;

import java.lang.reflect.Field;

public class BeanUtils {
    public static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return ;
        }
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().equals(targetField.getName()) && sourceField.getType().equals(targetField.getType())) {
                    try {
                        sourceField.setAccessible(true);
                        targetField.setAccessible(true);
                        targetField.set(target, sourceField.get(source));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
