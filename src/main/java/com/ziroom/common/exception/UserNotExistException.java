package com.ziroom.common.exception;

/**
 * 自定义异常
 * @author fangcheng
 * @date 2018/11/11
 */
public class UserNotExistException extends Exception {

    public UserNotExistException() {
        super();
    }

    public UserNotExistException(String message) {
        super(message);
    }
}
