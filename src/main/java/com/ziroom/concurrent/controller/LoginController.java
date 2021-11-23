package com.ziroom.concurrent.controller;

import com.ziroom.common.utils.IdGenerator;
import com.ziroom.common.utils.Resp;
import com.ziroom.concurrent.model.query.CookieCart;
import com.ziroom.concurrent.model.vo.CartPage;
import com.ziroom.concurrent.service.intf.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ziroom.concurrent.constant.consist.UrlConstant.*;

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
    public Resp<?> addCart(HttpServletRequest request, HttpServletResponse response, CookieCart obj) {
        loginService.addCart(request, response, obj);
        return Resp.success();
    }

    @ApiOperation("未登录-更改购物车商品数量")
    @PostMapping(UPDATE_CART)
    public Resp<?> updateCart(HttpServletRequest request, HttpServletResponse response, CookieCart obj) {
        loginService.updateCart(request, response, obj);
        return Resp.success();
    }

    @ApiOperation("未登录-删除购物车商品")
    @PostMapping(DEL_CART)
    public Resp delCart(HttpServletRequest request, HttpServletResponse response, Long productId) {
        loginService.delCart(request, response, productId);
        return Resp.success();
    }


    @ApiOperation("未登录-查询某个用户的购物车")
    @PostMapping(FIND_ALL)
    public Resp<CartPage<CookieCart>> findAll(HttpServletRequest request, HttpServletResponse response) {
        CartPage<CookieCart> result = loginService.findAll(request, response);
        return Resp.success(result);
    }


    @ApiOperation("登录-合并购物车")
    @PostMapping(MERGE_CART)
    public Resp<?> mergeCart(HttpServletRequest request, HttpServletResponse response, Long userId) {
        loginService.mergeCart(request, response, userId);
        return Resp.success();

    }

}
