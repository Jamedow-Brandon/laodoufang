package com.jamedow.laodoufang.web.huayao;

import com.jamedow.laodoufang.entity.Product;
import com.jamedow.laodoufang.entity.ProductExample;
import com.jamedow.laodoufang.mapper.ProductMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Jamedow
 * @date 2018/3/7
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class HuaYaoController {
    static Logger logger = LoggerFactory.getLogger(HuaYaoController.class);
    @Autowired
    private ProductMapper productMapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("huayao" + (StringUtils.isNotBlank(lang) ? "/" + lang : "") + "/index");
        view.addObject("lang", lang);
        return view;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(Integer productId, String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("huayao" + (StringUtils.isNotBlank(lang) ? "/" + lang : "") + "/detail");

        Product product = productMapper.selectByPrimaryKey(productId);

        view.addObject("product", product);
        view.addObject("lang", lang);
        return view;
    }

    @RequestMapping(value = "/enquirel", method = RequestMethod.GET)
    public ModelAndView enquirel(Integer productId, String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("huayao" + (StringUtils.isNotBlank(lang) ? "/" + lang : "") + "/enquirel");

        ProductExample example = new ProductExample();
        example.setOrderByClause("id asc");
        List<Product> products = productMapper.selectByExample(example);

        view.addObject("products", products);
        view.addObject("productId", productId);
        view.addObject("lang", lang);
        return view;
    }

}
