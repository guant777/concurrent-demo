package com.ziroom.concurrent.service.intf;

import com.ziroom.concurrent.model.query.CookieCart;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: concurrent-demo
 * @description: 登陆业务逻辑接口
 * @author: GuanTao
 * @create: 2021-11-22 19:36
 **/
public interface LoginService {
    /**
     * 未登录-添加购物车
     * @param obj 参数
     */
    void addCart(HttpServletRequest request, CookieCart obj);
}
