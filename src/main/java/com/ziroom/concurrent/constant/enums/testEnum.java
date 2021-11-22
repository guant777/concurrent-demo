package com.ziroom.concurrent.constant.enums;

import com.google.common.collect.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot_jpa
 * @description: 演示枚举类创建
 * @author:GuanTao
 * @create: 2020-04-28 17:51
 **/

@Getter
@AllArgsConstructor
public enum testEnum {
    /**
     * test1
     */
    test1("", ""),
    /**
     * test2
     */
    test2("", "");

    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;


    /**
     * 获取Name的map
     */
    public static final Map<String, String> NAME_MAP = Maps.newHashMap();
    /**
     * 获取Name的list
     */
    public static final List<String> NAME_List = Lists.newArrayList();

    
    static {
        EnumSet.allOf(testEnum.class).forEach(item -> {
            NAME_MAP.put(item.getCode(), item.getName());
            NAME_List.add(item.getName());
        });
    }
}
