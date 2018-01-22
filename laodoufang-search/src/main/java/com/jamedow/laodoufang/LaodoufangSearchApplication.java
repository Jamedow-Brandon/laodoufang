package com.jamedow.laodoufang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LaodoufangSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaodoufangSearchApplication.class, args);
    }
}
