package com.ziroom.common.utils;

/**
* @Description: 线程封闭（获取当前登陆人）
* @Author:GuanTao
* @Date: 2020/4/30 4:03 下午
*/
public class OperatorContext {

    private static final ThreadLocal<String> OPERATOR = new ThreadLocal<>();

    public static void setOperator(String operatorId) {
        OPERATOR.set(operatorId);
    }
    public static String getOperator() {
        return OPERATOR.get();
    }

}
