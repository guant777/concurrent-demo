package com.ziroom.concurrent.service.intf;

import com.ziroom.concurrent.model.query.CookieCart;
import com.ziroom.concurrent.model.vo.CartPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: concurrent-demo
 * @description: 登陆业务逻辑接口
 * @author: GuanTao
 * @create: 2021-11-22 19:36
 **/
public interface LoginService {
    /**
     * 未登录-添加购物车
     * @param request 请求参数
     * @param response 响应参数
     * @param obj 参数
     */
    void addCart(HttpServletRequest request, HttpServletResponse response, CookieCart obj);

    /**
     * 未登录-更改购物车商品数量
     *
     * @param request 请求参数
     * @param response 响应参数
     * @param obj 参数
     */
    void updateCart(HttpServletRequest request, HttpServletResponse response, CookieCart obj);

    /**
     * 未登录-删除购物车商品
     * @param request 请求参数
     * @param response 响应参数
     * @param productId 参数
     */
    void delCart(HttpServletRequest request, HttpServletResponse response, Long productId);

    /**
     * 未登录-查询某个用户的购物车
     *
     * @param request 请求参数
     * @param response 响应参数
     * @return CartPage CartPage<CookieCart>
     */
    CartPage<CookieCart> findAll(HttpServletRequest request, HttpServletResponse response);

    /**
     * 登录-合并购物车
     *
     * @param request 请求参数
     * @param response 响应参数
     * @param userId 用户id
     */
    void mergeCart(HttpServletRequest request, HttpServletResponse response, Long userId);

}
