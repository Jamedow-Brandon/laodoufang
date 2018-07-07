package com.jamedow.laodoufang.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 联动支付回调
     *
     * @param request
     * @param response
     */
    @RequestMapping("/umfPaySuccess")
    @ResponseBody
    public String getUmfPaySuccess(HttpServletRequest request, HttpServletResponse response) {
        return "0000";
    }
}
