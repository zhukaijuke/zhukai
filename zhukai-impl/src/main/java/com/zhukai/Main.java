package com.zhukai;

import org.apache.log4j.PropertyConfigurator;

public class Main {

	public static void main(String[] args) {
		String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		// 加载log4j
		PropertyConfigurator.configure(classpath + "log4j.properties");
		// 由Log4jContainer加载的日志配置不能很好的维护
		// 只使用默认的SpringContainer加载spring文件
		com.alibaba.dubbo.container.Main.main(args);
	}

}
