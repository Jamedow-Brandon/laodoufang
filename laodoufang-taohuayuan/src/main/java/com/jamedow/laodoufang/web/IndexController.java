package com.jamedow.laodoufang.web;

import com.jamedow.laodoufang.mapper.SysAreaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/")
public class IndexController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysAreaMapper sysAreaMapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String play() {
        return "index";
    }

}
