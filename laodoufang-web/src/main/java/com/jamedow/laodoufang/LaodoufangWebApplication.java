package com.jamedow.laodoufang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.jamedow.laodoufang.mapper")
public class LaodoufangWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaodoufangWebApplication.class, args);
    }

}
