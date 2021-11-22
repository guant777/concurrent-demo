package com.ziroom.common.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
* @Description: swagger2配置类
* @Author:GuanTao
* @Date: 2020/4/28 8:26 下午
*/
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {
        //可以添加多个header或参数
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder
                //参数类型支持header, cookie, body, query etc
                .parameterType("header")
                //参数名
                .name("uid")
                //默认值
                .defaultValue("60016010")
                .description("用户工号")
                //指定参数值的类型
                .modelRef(new ModelRef("string"))
                //非必需，这里是全局配置
                .required(true)
                .build();
        List<Parameter> aParameters = Lists.newArrayList();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.ziroom.concurrent.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiEndPointsInfo())
                .globalOperationParameters(aParameters);
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
                .title("高并发编程案例总结")
                .description("用于方便开发、总结")
                .contact(new Contact("关涛", "http://localhost:8085", "18518377703@163.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0")
                .build();
    }

}
