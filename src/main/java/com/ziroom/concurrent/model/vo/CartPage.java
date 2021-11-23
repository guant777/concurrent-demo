package com.ziroom.concurrent.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @program: concurrent-demo
 * @description:
 * @author: GuanTao
 * @create: 2021-11-23 10:30
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartPage<T> {
    /**
     * 数据数量
     */
    private Integer count;
    /**
     * 数据集合
     */
    private List<T> list;
}
