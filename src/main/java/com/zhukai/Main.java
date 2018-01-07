package com.zhukai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main implements EmbeddedServletContainerCustomizer {
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	@RequestMapping("/")
	public String getHello() {
		log.info("getHello in....");
		return "Hello Spring Boot .....";
	}

	// 修改访问的默认端口
	public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
		configurableEmbeddedServletContainer.setPort(80);
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		log.info("SpringBootApplication Service start.");
	}
}
