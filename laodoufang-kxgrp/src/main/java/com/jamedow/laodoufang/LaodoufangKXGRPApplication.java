package com.jamedow.laodoufang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.web.client.RestTemplate;

@Controller
@SpringBootApplication
//@EnableDiscoveryClient∂
@EnableWebMvc
public class LaodoufangKXGRPApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaodoufangKXGRPApplication.class, args);
    }

//    @Bean
//    @LoadBalanced
//    RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

}
