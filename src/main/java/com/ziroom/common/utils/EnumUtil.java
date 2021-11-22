package com.ziroom.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
* @Description:  枚举工具类
* @Author:GuanTao
* @Date: 2020/4/30 4:01 下午
*/
public class EnumUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnumUtil.class);

	public static String code2text(Number code, Class<? extends Enum> clazz) {
		String re = "";
		try {
			Method method;
			method = clazz.getDeclaredMethod("values");
			Object o = method.invoke(clazz);
			Enum[] array = (Enum[]) o;
			for (Enum t_obj : array) {
				Field field = t_obj.getClass().getDeclaredField("code");
				field.setAccessible(true);
				Object code1 = field.get(t_obj);
				if (code.equals(code1)) {
					Field textField = t_obj.getClass().getDeclaredField("text");
					textField.setAccessible(true);
					Object text = textField.get(t_obj);
					return (String) text;
				}
			}
		} catch (Exception e) {
			LOGGER.error("reflect error", e);
		}

		return re;
	}
}
