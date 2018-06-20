package com.jamedow.laodoufang.web.kxgrp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Jamedow
 * @date 2018/3/7
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class IndexController {
    static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
    public ModelAndView index(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }

    @RequestMapping(value = "services", method = RequestMethod.GET)
    public ModelAndView services(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("services");
        return view;
    }

    @RequestMapping(value = "gallery", method = RequestMethod.GET)
    public ModelAndView gallery(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("gallery");
        return view;
    }

    @RequestMapping(value = "codes", method = RequestMethod.GET)
    public ModelAndView codes(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("codes");
        return view;
    }

    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public ModelAndView contact(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("contact");
        return view;
    }
}
