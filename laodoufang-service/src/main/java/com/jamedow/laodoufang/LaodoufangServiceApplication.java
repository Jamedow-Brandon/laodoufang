package com.jamedow.laodoufang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@SpringBootApplication
@EnableWebMvc
@EnableAspectJAutoProxy
public class LaodoufangServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaodoufangServiceApplication.class, args);
    }

}
