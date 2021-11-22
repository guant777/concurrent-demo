package com.ziroom.common.exception;

import com.ziroom.common.utils.Resp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理
 *
 * @author fangcheng
 * @date 2018/11/11
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

	@ResponseBody
	@ExceptionHandler({Exception.class})
	public Resp handleException(Exception e) {

		// 异常信息
		String message = null;
		String errorCode = "-1";

		// 业务异常
		if (e instanceof ServiceException) {
			message = e.getMessage();
		} else if (e instanceof UserNotExistException) {
			message = "请登录";
		} else if (e instanceof ParameterException) {
			message = "参数异常：" + e.getMessage();
		} else if (e instanceof ThreeDataException) {
			message = "调用第三方接口异常：" + e.getMessage();
		} else if (e instanceof TokenExpiredException) {
			errorCode = "418";
			message = "用户token失效，请重新登陆" + e.getMessage();
		}
		// 响应信息默认显示异常信息
		if (StringUtils.isBlank(message)) {
			message = e != null ? e.toString() : "控制层异常处理中,异常对象Exception为空,请联系系统管理员!";
		}
		log.error(message, e);
		return Resp.error(errorCode, message);
	}
}
