package com.ziroom.concurrent.controller;

import com.ziroom.common.utils.IdGenerator;
import com.ziroom.concurrent.model.query.CookieCart;
import com.ziroom.concurrent.service.intf.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ziroom.concurrent.constant.consist.UrlConstant.ADD_CART;

/**
 * @program: concurrent-demo
 * @description: 登陆controller
 * @author: GuanTao
 * @create: 2021-11-22 19:30
 **/
@Slf4j
@RestController
@Api(tags = "登陆controller")
public class LoginController {

    @Resource
    private LoginService loginService;



    @ApiOperation("未登录-添加购物车")
    @PostMapping( ADD_CART)
    public void addCart(HttpServletRequest request, CookieCart obj) {
        loginService.addCart(request, obj);

    }

    /**
     * 未登录
     * 更改购物车商品数量
     */
    @PostMapping(value = "/updateCart")
    public void updateCart(CookieCart obj) {
        String cartId=this.getCookiesCartId();
        String key=COOKIE_KEY+cartId;
        this.redisTemplate.opsForHash().put(key, obj.getProductId().toString(),obj.getAmount());
    }
    /**
     * 未登录
     * 删除购物车商品
     */
    @PostMapping(value = "/delCart")
    public void delCart(Long productId) {
        String cartId=this.getCookiesCartId();
        String key=COOKIE_KEY+cartId;
        this.redisTemplate.opsForHash().delete(key, productId.toString());
    }
    /**
     * 未登录
     * 查询某个用户的购物车
     */
    @PostMapping(value = "/findAll")
    public CartPage findAll() {
        String cartId=this.getCookiesCartId();
        String key=COOKIE_KEY+cartId;

        CartPage<CookieCart> cartPage=new CartPage();
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
        cartPage.setCartList(cartList);
        return cartPage;
    }

    作者：小伙子vae
    链接：https://juejin.cn/post/7028759302697386021
    来源：稀土掘金
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
