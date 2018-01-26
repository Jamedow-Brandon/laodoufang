package com.jamedow.laodoufang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@EnableWebMvc
@EnableFeignClients
public class LaodoufangWebApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(LaodoufangWebApplication.class, args);
    }

    /**
     * 负载均衡配置
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
