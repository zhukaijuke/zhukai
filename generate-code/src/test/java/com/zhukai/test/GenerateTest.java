package com.zhukai.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.zhukai.service.SysGeneratorService;

import java.util.Date;

@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class GenerateTest extends AbstractJUnit4SpringContextTests  {
	
	private String[] tableNames = { "inventory_adjustment_detail" };
	
	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	@Test
	public void test() {
		sysGeneratorService.generatorCode(tableNames);
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		System.out.println(new Date().getTime());
	}
}
