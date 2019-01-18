package com.zhukai.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class WxSpApplication {

    private static final Double PI = Math.PI;

    private static final Double PK = 180 / PI;

    public static void main(String[] args) {
        SpringApplication.run(WxSpApplication.class, args);
    }

}
