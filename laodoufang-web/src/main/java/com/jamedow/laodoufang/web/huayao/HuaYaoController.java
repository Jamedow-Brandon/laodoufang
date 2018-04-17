package com.jamedow.laodoufang.web.huayao;

import com.jamedow.laodoufang.entity.Mail;
import com.jamedow.laodoufang.entity.Product;
import com.jamedow.laodoufang.entity.ProductExample;
import com.jamedow.laodoufang.mail.MailService;
import com.jamedow.laodoufang.mapper.ProductMapper;
import com.jamedow.laodoufang.mapper.SysAreaMapper;
import org.apache.xerces.dom.DeepNodeListImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

/**
 * @author Jamedow
 * @date 2018/3/7
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("huayao")
public class HuaYaoController {
    static Logger logger = LoggerFactory.getLogger(HuaYaoController.class);

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;

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

    @RequestMapping(value = "/mail/form", method = RequestMethod.GET)
    public String mail() {
        return "mail/form";
    }

    @RequestMapping(value = "/mail/send", method = RequestMethod.POST)
    @ResponseBody
    public String send(Integer productId, Integer demand, String requirementDescription, String companyName,
                       String district, String phone, String province, String region, String receiver) {

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("productId:").append(productId).append("\n");
        messageBuilder.append("demand:").append(demand).append("\n");
        messageBuilder.append("requirementDescription:").append(requirementDescription).append("\n");
        messageBuilder.append("companyName:").append(companyName).append("\n");
        messageBuilder.append("district:").append(district).append("\n");
        messageBuilder.append("phone:").append(phone).append("\n");
        messageBuilder.append("province:").append(province).append("\n");
        messageBuilder.append("region:").append(region).append("\n");
        messageBuilder.append("receiver:").append(receiver);
        Mail mail = new Mail.Builder("smtp.qq.com", "1472541865@qq.com", "tnnfmpbgsjckbade")
                .sender("1472541865@qq.com").receiver("563150601@qq.com").name(companyName).message(messageBuilder.toString()).build();
        MailService.sendMail(mail);
        return "success";
    }

    public static void main(String[] args) {
        long lasting = System.currentTimeMillis();

        try {
            File f = new File("/Users/brandon/IdeaProjects/laodoufang/laodoufang-web/src/main/resources/LocList.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            DeepNodeListImpl nl = (DeepNodeListImpl) doc.getElementsByTagName("CountryRegion");
            for (int i = 0; i < nl.getLength(); i++) {
                logger.info(nl.item(1).getNodeName());


                System.out.print("车牌号码:" + doc.getElementsByTagName("NO").item(i).getFirstChild().getNodeValue());
                System.out.println("车主地址:" + doc.getElementsByTagName("ADDR").item(i).getFirstChild().getNodeValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}