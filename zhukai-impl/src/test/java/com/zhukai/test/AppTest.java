package com.zhukai.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.zhukai.manager.UserManager;
import com.zhukai.model.User;

/**
 * Unit test for simple App.
 */
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class AppTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private UserManager userManager;
	
	@Test
	public void test() {
		User user = userManager.findUserByUserNameAndPassword("zhukai", "123456");
		System.out.println(user.getUserName());
		System.out.println(user.getEmail());
		System.out.println(user.getPhone());
		System.out.println(user.getCreateTime());
		System.out.println(user.getRemark());
	}
}
