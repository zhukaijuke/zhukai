package com.zhukai;

import org.apache.log4j.PropertyConfigurator;

public class Main {

	public static void main(String[] args) {
		String classpath = Thread.currentThread().getContextClassLoader ().getResource("").getPath();
		
		PropertyConfigurator.configure(classpath + "log4j.properties");
		
		com.alibaba.dubbo.container.Main.main(args);
	}

}
