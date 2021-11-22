package com.ziroom.common.exception;

/**
 * 第三方数据源获取的数据错误
 * @author fangcheng
 * @date 2018/11/26
 */
public class ThreeDataException extends Exception {
    public ThreeDataException(String msg) {
        super(msg);
    }
}
