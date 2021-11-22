package com.ziroom.concurrent.service.intf;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: concurrent-demo
 * @description: 防刷业务逻辑接口
 * @author: GuanTao
 * @create: 2021-11-22 18:51
 **/
public interface PreventBrushingService {
    /**
     * redis+lua防刷接口
     * @param request 请求参数
     * @return String
     */
    String houses(HttpServletRequest request);
}
