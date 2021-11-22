package com.ziroom.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: concurrent-demo
 * @description:
 * @author: GuanTao
 * @create: 2021-11-22 19:46
 **/
@Component
public class IdGenerator {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String ID_KEY = "id:generator:cart";

    /**
     * 生成全局唯一id
     */
    public Long incrementId() {
        long n=stringRedisTemplate.opsForValue().increment(ID_KEY);
        return n;
    }
}
