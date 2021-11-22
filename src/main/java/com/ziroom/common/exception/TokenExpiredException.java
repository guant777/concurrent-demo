package com.ziroom.common.exception;

/**
 * token过期异常
 * @author fangcheng
 * @date 2018/12/4
 */
public class TokenExpiredException extends Exception {
    public TokenExpiredException(String exceptionMessage){
        super(exceptionMessage);
    }
}
