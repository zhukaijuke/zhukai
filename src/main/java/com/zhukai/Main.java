package com.zhukai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class Main {
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	@RequestMapping("/")
	public String getHello(Model model) {
		log.info("getHello in....");
		model.addAttribute("hello", "Thymeleaf");
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		log.info("SpringBootApplication Service start...");
	}
}
