package com.lee.oa.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.NotWritablePropertyException;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName BeanUtil
 * @Description Bean 工具类
 * @Author lihongliang
 * @Date 2025/11/6 19:22
 * @Version 1.0
 */
public class BeanUtil {

    /**
     * 拷贝属性
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        copyObjectProperties(source, target, false);
    }

    /**
     * 拷贝属性，只拷贝非空的属性
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesWithNonNull(Object source, Object target) {
        copyObjectProperties(source, target, true);
    }

    /**
     * 拷贝对象属性
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreNull 是否忽略空属性
     */
    private static void copyObjectProperties(Object source, Object target, Boolean ignoreNull) {
        BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);

        Set<String> ignoreProperties = new HashSet<>();

        PropertyDescriptor[] propertyDescriptors = sourceWrapper.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if ("class".equals(propertyName)) {
                continue;
            }

            try {
                Object value = sourceWrapper.getPropertyValue(propertyName);
                // 只复制非null属性
                if (ignoreNull) {
                    if (value != null) {
                        targetWrapper.setPropertyValue(propertyName, value);
                    }
                } else {
                    targetWrapper.setPropertyValue(propertyName, value);
                }

            } catch (NotWritablePropertyException e) {
                // 忽略无法写入的属性
                ignoreProperties.add(propertyName);
            } catch (InvalidPropertyException e) {
                // 忽略其他异常
            }
        }
    }
}