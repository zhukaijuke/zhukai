package com.zhukai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhukai.manager.UserManager;
import com.zhukai.model.User;

@Controller
public class DemoController {
	
	private static final Logger log = LoggerFactory.getLogger(DemoController.class);
	
	// @Reference 引入dubbo服务, 使用@Autowire会报错
	@Reference
	private UserManager userManager;
	
	@RequestMapping("/")
	public String getHello(Model model) {
		log.info("getHello in....");
		User user = userManager.findUserByUserNameAndPassword("zhukai", "123456");
		model.addAttribute("user", user);
		model.addAttribute("hello", "Thymeleaf");
		return "index";
	}
}
