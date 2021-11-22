package com.ziroom.concurrent.service.impl;

import com.ziroom.common.utils.IpUtils;
import com.ziroom.concurrent.service.intf.PreventBrushingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @program: concurrent-demo
 * @description: 防刷业务逻辑接口实现
 * @author: GuanTao
 * @create: 2021-11-22 18:51
 **/
@Slf4j
@Service
public class PreventBrushingServiceImpl implements PreventBrushingService {

    @Resource
    private DefaultRedisScript<Long> limitScript;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * redis+lua防刷接口
     * @param request 请求参数
     * @return String
     */
    @Override
    public String houses(HttpServletRequest request) {
        //获取请求ip
        String ip = IpUtils.getIpAddr(request);
        //设置redis 的key
        List<String> keys = Collections.singletonList("HouseAPI:" + ip);
        /*
        执行lua脚本，execute方法有3个参数，第一个参数是lua脚本对象，第二个是key列表，第三个是lua的参数数组
        30代表30秒 ，10代表超过10次，也就是说同个ip 30秒内不能超过10次请求
        */
        Long n = this.stringRedisTemplate.execute(this.limitScript, keys, "30", "10");
        String result="";
        //非法请求
        if (Objects.nonNull(n) && n == 0) {
            result= "非法请求";
        } else {
            result= "返回房源列表";
        }
        log.info("ip={}请求结果：{}", ip,result);
        return result;
    }
}
