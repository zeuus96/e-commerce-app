package com.link.linkbackend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CustomObjectUtils {
    private CustomObjectUtils() {
    }

    public static boolean allNotNull(Object target) {
        if (target == null) {
            return true;
        }

        List<Field> allFields = new ArrayList<>();
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            allFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        return allFields.stream()
                .allMatch(field -> {
                    ReflectionUtils.makeAccessible(field);
                    try {
                        return ObjectUtils.isEmpty(field.get(target));
                    } catch (IllegalAccessException e) {
                        log.error("Error accessing field value: {}", e.getMessage());
                        return false;
                    }
                });
    }
}
