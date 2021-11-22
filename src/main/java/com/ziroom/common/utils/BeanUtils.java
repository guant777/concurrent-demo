package com.ziroom.common.utils;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * bean 工具
 *
 * @author fangcheng
 * @date 2018/12/17
 */
public class BeanUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtils.class);

	private BeanUtils() {
	}

	/**
	 * Bean --> Map
	 * 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	 *
	 * @param obj bean
	 * @return map
	 */
	public static Map<String, String> transBean2Map4Http(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, String> map = Maps.newHashMap();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (value != null) {
						map.put(key, String.valueOf(value));
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error("transBean2Map Error ", e);
		}

		return map;
	}

	/**
	 * Map --> Bean
	 * 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	 *
	 * @param map map
	 * @param obj bean
	 */
	public static void transMap2Bean(Map<String, Object> map, Object obj) {

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}

			}

		} catch (Exception e) {
			LOGGER.error("transMap2Bean Error ", e);
		}
	}

	/**
	 * Bean --> Map
	 * 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	 *
	 * @param obj bean
	 * @return map
	 */
	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = Maps.newHashMap();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			LOGGER.error("transBean2Map Error ", e);
		}

		return map;
	}
}
