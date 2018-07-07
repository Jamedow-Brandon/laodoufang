package com.jamedow.laodoufang.web.cms;

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
@RequestMapping("/cms/article")
public class ArticleController {
    static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView services(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("cms/add");
        return view;
    }
}
