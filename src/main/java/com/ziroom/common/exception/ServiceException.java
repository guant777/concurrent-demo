package com.ziroom.common.exception;

/**
 * 业务异常处理类
 * @author weilinzi
 * @Date 2018/11/26 13:15
 */
public class ServiceException extends RuntimeException{

    public ServiceException(String exceptionMessage){
        super(exceptionMessage);
    }
}
