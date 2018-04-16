package com.jamedow.laodoufang.web.huayao;

import com.jamedow.laodoufang.entity.Mail;
import com.jamedow.laodoufang.entity.Product;
import com.jamedow.laodoufang.entity.ProductExample;
import com.jamedow.laodoufang.mail.MailService;
import com.jamedow.laodoufang.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Jamedow
 * @date 2018/3/7
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("huayao")
public class HuaYaoController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductMapper productMapper;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "huayao/index";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(Integer productId) {
        ModelAndView view = new ModelAndView();
        view.setViewName("huayao/detail");

        view.addObject("productId", productId);
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

    @RequestMapping(value = "/mailform", method = RequestMethod.GET)
    public String mail() {
        return "mail/form";
    }

    @RequestMapping(value = "/mailsend", method = RequestMethod.POST)
    @ResponseBody
    public String send(Integer productId, Integer demand, String requirementDescription, String companyName,
                       String district, String phone, String province, String region, String receiver) {

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("productId:").append(productId);
        messageBuilder.append("demand:").append(demand);
        messageBuilder.append("requirementDescription:").append(requirementDescription);
        messageBuilder.append("companyName:").append(companyName);
        messageBuilder.append("district:").append(district);
        messageBuilder.append("phone:").append(phone);
        messageBuilder.append("province:").append(province);
        messageBuilder.append("region:").append(region);
        messageBuilder.append("receiver:").append(receiver);
        Mail mail = new Mail.Builder("smtp.qq.com", "1472541865@qq.com", "tnnfmpbgsjckbade")
                .sender("1472541865@qq.com").receiver(receiver).name(companyName).message(messageBuilder.toString()).build();
        MailService.sendMail(mail);
        return "success";
    }
}
