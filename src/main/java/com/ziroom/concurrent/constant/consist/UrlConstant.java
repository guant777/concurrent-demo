package com.ziroom.concurrent.constant.consist;

/**
 * @program: risk-management
 * @description: 程序入口常量管理
 * @author:GuanTao
 * @create: 2019-09-05 10:20
 **/
public interface UrlConstant {

	/**
	 * redis+lua防刷接口
	 */
	String HOUSES = "/houses";
	/**
	 * 未登录-添加购物车
	 */
	String ADD_CART = "/addCart";
	/**
	 * 未登录-更改购物车商品数量
	 */
	String UPDATE_CART = "/updateCart";
	/**
	 * 未登录-删除购物车商品
	 */
	String DEL_CART = "/delCart";

	/**
	 * 未登录-查询某个用户的购物车
	 */
	String FIND_ALL = "/findAll";
	/**
	 *
	 */
	String MERGE_CART = "/mergeCart";

}
