package com.zhukai.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.zhukai.service.SysGeneratorService;

@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class GenerateTest extends AbstractJUnit4SpringContextTests  {
	
	private String[] tableNames = { "bas_vehicle" };
	
	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	@Test
	public void test() {
		sysGeneratorService.generatorCode(tableNames);
	}
	
}
