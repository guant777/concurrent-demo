package com.ziroom.concurrent.model.query;

import lombok.Data;

/**
 * @program: concurrent-demo
 * @description: 登录购物车实体类
 * @author: GuanTao
 * @create: 2021-11-22 19:27
 **/
@Data
public class Cart {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品数量
     */
    private int amount;
}
