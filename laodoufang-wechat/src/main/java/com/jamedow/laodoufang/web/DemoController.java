package com.jamedow.laodoufang.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Jamedow on 2018/2/7.
 */
@Controller
@RequestMapping("demo")
public class DemoController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("js")
    public ModelAndView wechatJs() {
        ModelAndView view = new ModelAndView();
        view.setViewName("demo");

        return view;
    }
}
