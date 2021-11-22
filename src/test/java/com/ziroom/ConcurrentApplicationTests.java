package com.ziroom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 所有测试类，需继承改类
 */
@TestPropertySource(properties = {
		"spring.profiles.active=dev"
})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcurrentApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConcurrentApplicationTests {

	@Autowired
	protected TestRestTemplate testRestTemplate;

	@Before
	public void init(){
		System.out.println("开始测试-------------------------");
	}

	@After
	public void after(){
		System.out.println("测试结束-------------------------");
	}

	@Test
	public void contextLoads() {

	}

}
