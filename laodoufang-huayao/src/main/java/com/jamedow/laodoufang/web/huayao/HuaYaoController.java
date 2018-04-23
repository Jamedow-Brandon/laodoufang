package com.jamedow.laodoufang.web.huayao;

import com.jamedow.laodoufang.entity.Product;
import com.jamedow.laodoufang.entity.ProductExample;
import com.jamedow.laodoufang.mapper.ProductMapper;
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
    private final String COMPANY_MAIL = "company_mail";
    @Autowired
    private ProductMapper productMapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "huayao/index";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(Integer productId) {
        ModelAndView view = new ModelAndView();
        view.setViewName("huayao/detail");

        Product product = productMapper.selectByPrimaryKey(productId);

        view.addObject("product", product);
        return view;
    }

    @RequestMapping(value = "/enquirel", method = RequestMethod.GET)
    public ModelAndView enquirel(Integer productId) {
        ModelAndView view = new ModelAndView();
        view.setViewName("huayao/enquirel");

        List<Product> products = productMapper.selectByExample(new ProductExample());

        view.addObject("products", products);
        view.addObject("productId", productId);
        return view;
    }

}
