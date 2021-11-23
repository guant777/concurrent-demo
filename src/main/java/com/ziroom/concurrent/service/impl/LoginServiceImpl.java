package com.ziroom.concurrent.service.impl;

import com.ziroom.common.utils.IdGenerator;
import com.ziroom.concurrent.model.query.CookieCart;
import com.ziroom.concurrent.model.vo.CartPage;
import com.ziroom.concurrent.service.intf.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ziroom.concurrent.constant.consist.OtherConstant.COOKIE_KEY;

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
    private RedisTemplate redisTemplate;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 未登录-添加购物车
     *
     * @param request 请求参数
     * @param response 响应参数
     * @param obj     参数
     */
    @Override
    public void addCart(HttpServletRequest request, HttpServletResponse response, CookieCart obj) {
        //获取一个Cookies的cardId
        String cartId = this.getCookiesCartId(request, response);
        String key = COOKIE_KEY + cartId;
        Boolean hasKey = redisTemplate.opsForHash().getOperations().hasKey(key);
        //存在
        if (hasKey) {
            //保存key为购物车，hash的key为商品id，value为数量
            this.redisTemplate.opsForHash().put(key, obj.getProductId().toString(), obj.getAmount());
        } else {
            this.redisTemplate.opsForHash().put(key, obj.getProductId().toString(), obj.getAmount());
            this.redisTemplate.expire(key, 90, TimeUnit.DAYS);
        }
    }


    /**
     * 未登录-更改购物车商品数量
     *
     * @param request 请求参数
     * @param response 响应参数
     * @param obj 参数
     */
    @Override
    public void updateCart(HttpServletRequest request, HttpServletResponse response, CookieCart obj) {
        String cartId=this.getCookiesCartId(request, response);
        String key=COOKIE_KEY+cartId;
        this.redisTemplate.opsForHash().put(key, obj.getProductId().toString(),obj.getAmount());
    }


    /**
     * 未登录-删除购物车商品
     *
     * @param request 请求参数
     * @param response 响应参数
     * @param productId 参数
     */
    @Override
    public void delCart(HttpServletRequest request, HttpServletResponse response, Long productId) {
        String cartId=this.getCookiesCartId(request, response);
        String key=COOKIE_KEY+cartId;
        this.redisTemplate.opsForHash().delete(key, productId.toString());
    }

    /**
     * 未登录-查询某个用户的购物车
     *
     * @param request 请求参数
     * @param response 响应参数
     * @return CartPage<CookieCart>
     */
    @Override
    public CartPage<CookieCart> findAll(HttpServletRequest request, HttpServletResponse response) {
        String cartId=this.getCookiesCartId(request, response);
        String key=COOKIE_KEY+cartId;
        CartPage<CookieCart> cartPage=new CartPage<>();
        //查询该用户购物车的总数
        long size=this.redisTemplate.opsForHash().size(key);
        cartPage.setCount((int)size);

        //查询购物车的所有商品
        Map<String,Integer> map= this.redisTemplate.opsForHash().entries(key);
        List<CookieCart> cartList=new ArrayList<>();
        for (Map.Entry<String,Integer> entry:map.entrySet()){
            CookieCart cart=new CookieCart();
            cart.setProductId(Long.parseLong(entry.getKey()));
            cart.setAmount(entry.getValue());
            cartList.add(cart);
        }
        cartPage.setList(cartList);
        return cartPage;
    }

    /**
     * 登录-合并购物车
     *
     * @param request 请求参数
     * @param response 响应参数
     * @param userId 用户id
     */
    @Override
    public void mergeCart(HttpServletRequest request, HttpServletResponse response, Long userId) {
        //第一步：提取未登录用户的cookie的购物车数据
        String cartId=this.getCookiesCartId(request, response);
        String keycookie=COOKIE_KEY+cartId;
        Map<String,Integer> map= this.redisTemplate.opsForHash().entries(keycookie);

        //第二步：把cookie中得购物车合并到登录用户的购物车
        String keyuser = "cart:user:" + userId;
        this.redisTemplate.opsForHash().putAll(keyuser,map);

        //第三步：删除redis未登录的用户cookies的购物车数据
        this.redisTemplate.delete(keycookie);

        //第四步：删除未登录用户cookies的cartid
        Cookie cookie=new Cookie("cartId",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 获取cookies
     */
    public String getCookiesCartId(HttpServletRequest request, HttpServletResponse response) {
        //第一步：先检查cookies是否有cartid
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cartId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        //第二步：cookies没有cartid，直接生成全局id，并设置到cookie里面
        //生成全局唯一id
        long id = this.idGenerator.incrementId();
        //设置到cookies
        Cookie cookie = new Cookie("cartId", String.valueOf(id));
        response.addCookie(cookie);
        return id + "";
    }
}
