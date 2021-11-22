package com.ziroom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SpringBoot 启动程序
 *
 * @author Administratormvn
 * java -jar xx.jar --spring.profiles.active=dev
 */

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class ConcurrentApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConcurrentApplication.class, args);
	}

}



