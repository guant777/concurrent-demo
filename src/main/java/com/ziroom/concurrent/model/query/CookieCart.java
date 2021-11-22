package com.ziroom.concurrent.model.query;

import lombok.Data;

/**
 * @program: concurrent-demo
 * @description: 未登录购物车实体类
 * @author: GuanTao
 * @create: 2021-11-22 19:26
 **/
@Data
public class CookieCart {
    /**
     * 商品id商品id
     */
    private Long productId;
    /**
     * 商品数量
     */
    private int amount;
}
