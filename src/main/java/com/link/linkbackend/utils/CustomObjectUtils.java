package com.link.linkbackend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Slf4j
public class CustomObjectUtils {
    private CustomObjectUtils(){}
    public static boolean allNotNull(Object target) {
        if (target == null) {
            return false;
        }
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
            try {
                Object fieldValue = field.get(target);
                if (!allNotNull(fieldValue)) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                log.error("Error accessing field value: {}", e.getMessage());
                return false;
            }
        }
        return true;
    }
}
