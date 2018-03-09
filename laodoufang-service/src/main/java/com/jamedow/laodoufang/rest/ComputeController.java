package com.jamedow.laodoufang.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description
 * <p>
 * Created by Administrator on 2017/11/21.
 */
@RestController
public class ComputeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        List<ServiceInstance> instanceLst = client.getInstances("eureka-service");
        System.out.println("=====================================");
        for (ServiceInstance s : instanceLst) {
            System.out.println(s.getPort() + ":" + s.getHost());
        }
        Integer r = a + b;
        return r;
    }
}