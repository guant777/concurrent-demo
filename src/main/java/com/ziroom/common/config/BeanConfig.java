package com.ziroom.common.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;


/**
 * Bean配置类
 * 备注:所有自定义bean在此进行配置
 * @author GuanTao
 * @date 2019/09/19
 */
@Configuration
public class BeanConfig {


    /**
     * 创建lua脚本对象,并且注入IOC容器中
     *
     * @return DefaultRedisScript
     */
    @Bean
    public DefaultRedisScript<Long> limitScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

}
