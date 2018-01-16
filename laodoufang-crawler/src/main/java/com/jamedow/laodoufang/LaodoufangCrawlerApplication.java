package com.jamedow.laodoufang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jamedow.laodoufang.mapper")
public class LaodoufangCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaodoufangCrawlerApplication.class, args);
    }
}
