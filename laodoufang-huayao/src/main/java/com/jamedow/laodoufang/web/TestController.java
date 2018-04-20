package com.jamedow.laodoufang.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/26.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("test")
public class TestController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "three", method = RequestMethod.GET)
    public String play() {
        return "test/three";
    }
}
