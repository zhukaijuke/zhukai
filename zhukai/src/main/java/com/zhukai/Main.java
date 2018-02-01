package com.zhukai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// 按下面ImportResource的方式引入spring-dubbo.xml和相关配置也可以使用dubbo服务, 
// 为了更好融合springboot的理念结合注解和application.properties配置方式来使用dubbo服务, 因此使用spring-boot-starter-dubbo包
// @ImportResource(value = {"classpath*:spring/spring-dubbo.xml"})
@SpringBootApplication
public class Main {
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		log.info("SpringBootApplication Service start...");
	}
}
