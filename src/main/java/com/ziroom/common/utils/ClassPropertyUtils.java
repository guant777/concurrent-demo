package com.ziroom.common.utils;

import com.google.common.collect.Sets;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Set;

/**
 * 类中属性Utils
 *
 * @author GuanTao
 * @date 2018/12/4 10:30
 */
public class ClassPropertyUtils {

    /**
     * 获取类中空属性,用户对象属性复制时,不复制空对象
     *
     * @return 空属性Set
     */
    public static String[] getNullPropertyNames(Object entity) {
        final BeanWrapper src = new BeanWrapperImpl(entity);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = Sets.newHashSet();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
