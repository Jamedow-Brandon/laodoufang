package com.jamedow.laodoufang.web.huayao;

import com.jamedow.laodoufang.entity.Mail;
import com.jamedow.laodoufang.entity.Product;
import com.jamedow.laodoufang.entity.ProductExample;
import com.jamedow.laodoufang.entity.SysArea;
import com.jamedow.laodoufang.mail.MailService;
import com.jamedow.laodoufang.mapper.ProductMapper;
import com.jamedow.laodoufang.mapper.SysAreaMapper;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
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

    @RequestMapping(value = "/import/area/txt", method = RequestMethod.GET)
    @ResponseBody
    public void importTxt() {
        try {
            /* 读入TXT文件 */
            String pathname = "C:\\workspace\\laodoufang\\laodoufang-web\\src\\main\\resources\\area.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                String code = line.substring(0, 6);
                line = line.substring(line.indexOf("\t") + 1, line.length());
                String name = line.substring(0, line.indexOf("\t"));


            }
        } catch (Exception e) {

        }
    }

    @RequestMapping(value = "/import/area/xml", method = RequestMethod.GET)
    @ResponseBody
    public void importXml() {
        //1、创建一个DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //2、创建一个DocumentBuilder的对象
        try {
            //创建DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            //3、通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
            /*注意导入Document对象时，要导入org.w3c.dom.Document包下的*/
            Document document = db.parse("C:\\workspace\\laodoufang\\laodoufang-web\\src\\main\\resources\\LocList.xml");//传入文件名可以是相对路径也可以是绝对路径
            //获取所有book节点的集合
            NodeList bookList = document.getElementsByTagName("CountryRegion");
            //通过nodelist的getLength()方法可以获取bookList的长度
            System.out.println("一共有" + bookList.getLength() + "本书");
            //遍历每一个book节点
            for (int i = 0; i < bookList.getLength(); i++) {
                System.out.println("=================下面开始遍历第" + (i + 1) + "本书的内容=================");
                //❤未知节点属性的个数和属性名时:
                //通过 item(i)方法 获取一个book节点，nodelist的索引值从0开始
                Node country = bookList.item(i);

                NamedNodeMap countryNodeMap = country.getAttributes();

                String countryName = countryNodeMap.getNamedItem("Name").getNodeValue();
                String countryId = countryNodeMap.getNamedItem("Code").getNodeValue();

                SysArea countryEntity = new SysArea();

                countryEntity.setName(countryName);
                countryEntity.setCode(countryId);

                sysAreaMapper.insert(countryEntity);

                //获取book节点的所有属性集合
                NamedNodeMap attrs = country.getAttributes();
                System.out.println("第 " + (i + 1) + "本书共有" + attrs.getLength() + "个属性");
                //遍历book的属性
                for (int j = 0; j < attrs.getLength(); j++) {
                    //通过item(index)方法获取book节点的某一个属性
                    Node attr = attrs.item(j);
                    //获取属性名
                    System.out.print("属性名：" + attr.getNodeName());
                    //获取属性值
                    System.out.println("--属性值" + attr.getNodeValue());
                }
                //❤已知book节点有且只有1个id属性:
             /*
              //前提：已经知道book节点有且只能有1个id属性
              //将book节点进行强制类型转换，转换成Element类型
              Element book1 = (Element) bookList.item(i);
              //通过getAttribute("id")方法获取属性值
              String attrValue = book1.getAttribute("id");
              System.out.println("id属性的属性值为" + attrValue);
              */

                //解析book节点的子节点
                NodeList childNodes = country.getChildNodes();
                //遍历childNodes获取每个节点的节点名和节点值
                System.out.println("第" + (i + 1) + "本书共有" + childNodes.getLength() + "个子节点");
                for (int k = 0; k < childNodes.getLength(); k++) {
                    //区分出text类型的node以及element类型的node
                    if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        //获取了element类型节点的节点名
                        System.out.print("第" + (k + 1) + "个节点的节点名：" + childNodes.item(k).getNodeName());
                        //获取了element类型节点的节点值
                        System.out.println("--节点值是：" + childNodes.item(k).getFirstChild().getNodeValue());
//                        System.out.println("--节点值是：" + childNodes.item(k).getTextContent());

                        Node state = childNodes.item(k);

                        NamedNodeMap stateNodeMap = state.getAttributes();

                        String stateName = stateNodeMap.getNamedItem("Name").getNodeValue();
                        String stateId = stateNodeMap.getNamedItem("Code").getNodeValue();


                        SysArea stateEntity = new SysArea();

                        stateEntity.setName(stateName);
                        stateEntity.setCode(stateId);
                        stateEntity.setParentId(countryEntity.getId());

                        sysAreaMapper.insert(stateEntity);

                        //解析book节点的子节点
                        NodeList cityNodes = state.getChildNodes();
                        //遍历cityNodes获取每个节点的节点名和节点值
                        System.out.println("第" + (k + 1) + "城市共有" + cityNodes.getLength() + "个地区");
                        for (int l = 0; l < cityNodes.getLength(); l++) {
                            //区分出text类型的node以及element类型的node
                            if (cityNodes.item(l).getNodeType() == Node.ELEMENT_NODE) {
                                //获取了element类型节点的节点名

                                Node city = cityNodes.item(l);
                                NamedNodeMap cityNodeMap = city.getAttributes();


                                String cityName = cityNodeMap.getNamedItem("Name").getNodeValue();
                                String cityId = cityNodeMap.getNamedItem("Code").getNodeValue();


                                System.out.print("第" + (l + 1) + "个地区名：" + cityName);
                                System.out.println("--地区值是：" + cityId);
                            }
                        }
                    }
                }
                System.out.println("======================结束遍历第" + (i + 1) + "本书的内容=================");
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
