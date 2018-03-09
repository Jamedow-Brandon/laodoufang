package com.jamedow.laodoufang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Administrator
 */
@EnableDiscoveryClient
@SpringBootApplication
public class LaodoufangServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaodoufangServiceApplication.class, args);
    }
}
