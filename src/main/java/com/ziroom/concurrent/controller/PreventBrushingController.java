package com.ziroom.concurrent.controller;

import com.ziroom.common.utils.IpUtils;
import com.ziroom.common.utils.Resp;
import com.ziroom.concurrent.service.intf.PreventBrushingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.ziroom.concurrent.constant.consist.UrlConstant.HOUSES;

/**
 * @program: springboot_jpa
 * @description: 演示用例视图层
 * @author: GuanTao
 * @create: 2020-04-28 17:54
 **/

@Slf4j
@RestController
@Api(tags = "接口防刷controller")
public class PreventBrushingController {

    /**
     * 防止刷业务接口
     */
    @Resource
    PreventBrushingService preventBrushingService;

    /**
     * redis+lua防刷接口
     *
     * @param request 请求参数
     * @return String
     */
    @ApiOperation("redis+lua防刷接口")
    @GetMapping(HOUSES)
    public Resp<String> houses(HttpServletRequest request) {
        String result = preventBrushingService.houses(request);
        return Resp.success(result);
    }

}
