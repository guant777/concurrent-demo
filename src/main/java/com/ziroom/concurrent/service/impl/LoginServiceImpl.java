package com.ziroom.concurrent.service.impl;

import com.ziroom.common.utils.IdGenerator;
import com.ziroom.concurrent.model.query.CookieCart;
import com.ziroom.concurrent.service.intf.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @program: concurrent-demo
 * @description:
 * @author: GuanTao
 * @create: 2021-11-22 19:37
 **/
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private IdGenerator idGenerator;
    /**
     * 未登录-添加购物车
     *
     * @param request 请求参数
     * @param obj 参数
     */
    @Override
    public void addCart(HttpServletRequest request, CookieCart obj) {
        //获取一个Cookies的cardId
        String cartId=this.getCookiesCartId(request);
        String key=COOKIE_KEY+cartId;
        Boolean hasKey = redisTemplate.opsForHash().getOperations().hasKey(key);
        //存在
        if(hasKey){
            //保存key为购物车，hash的key为商品id，value为数量
            this.redisTemplate.opsForHash().put(key, obj.getProductId().toString(),obj.getAmount());
        }else{
            this.redisTemplate.opsForHash().put(key, obj.getProductId().toString(), obj.getAmount());
            this.redisTemplate.expire(key,90, TimeUnit.DAYS);
        }
    }

    /**
     * 获取cookies
     */
    public  String getCookiesCartId(HttpServletRequest request){
        //第一步：先检查cookies是否有cartid
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("cartId".equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        //第二步：cookies没有cartid，直接生成全局id，并设置到cookie里面
        //生成全局唯一id
        long id=this.idGenerator.incrementId();
        //设置到cookies
        Cookie cookie=new Cookie("cartId",String.valueOf(id));
        response.addCookie(cookie);
        return id+"";
    }
}
