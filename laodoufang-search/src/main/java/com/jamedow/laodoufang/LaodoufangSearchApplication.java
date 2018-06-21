package com.jamedow.laodoufang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.jamedow.laodoufang.mapper")
public class LaodoufangSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaodoufangSearchApplication.class, args);
    }
}
