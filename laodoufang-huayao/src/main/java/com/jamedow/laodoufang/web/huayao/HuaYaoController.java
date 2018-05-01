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

        //初始化语言
        initLanguage(view, lang, "/index");

        return view;
    }


    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(Integer productId, String lang) {
        ModelAndView view = new ModelAndView();

        Product product = productMapper.selectByPrimaryKey(productId);

        view.addObject("product", product);

        //初始化语言
        initLanguage(view, lang, "/detail");

        return view;
    }

    @RequestMapping(value = "/enquirel", method = RequestMethod.GET)
    public ModelAndView enquirel(Integer productId, String lang) {
        ModelAndView view = new ModelAndView();

        ProductExample example = new ProductExample();
        example.setOrderByClause("id asc");
        List<Product> products = productMapper.selectByExample(example);

        view.addObject("products", products);
        view.addObject("productId", productId);

        //初始化语言
        initLanguage(view, lang, "/enquirel");

        return view;
    }

    /**
     * 初始化语言
     *
     * @param lang 语言
     * @param view 页面
     */
    private void initLanguage(ModelAndView view, String lang, String viewName) {
        view.setViewName("huayao" + (StringUtils.isNotBlank(lang) ? "/" + lang : "") + viewName);
        view.addObject("lang", lang);
    }
}
