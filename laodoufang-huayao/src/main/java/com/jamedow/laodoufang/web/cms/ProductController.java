package com.jamedow.laodoufang.web.cms;

import com.jamedow.laodoufang.entity.Product;
import com.jamedow.laodoufang.entity.ProductExample;
import com.jamedow.laodoufang.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(String lang) {
        ModelAndView view = new ModelAndView();
        view.setViewName("huayao/product/list");
        List<Product> products = productMapper.selectByExample(new ProductExample());

        view.addObject("products", products);

        return view;
    }

    @RequestMapping(value = "form/{id}", method = RequestMethod.GET)
    public ModelAndView form(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        view.setViewName("huayao/product/form");
        Product product = productMapper.selectByPrimaryKey(id);

        view.addObject("product", product);

        return view;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(Product product) {
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/product/list");
        productMapper.updateByPrimaryKeySelective(product);
        return view;
    }
}
