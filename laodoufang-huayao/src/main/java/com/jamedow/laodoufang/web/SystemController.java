package com.jamedow.laodoufang.web;

import com.jamedow.laodoufang.entity.*;
import com.jamedow.laodoufang.mail.MailService;
import com.jamedow.laodoufang.mapper.SysAreaMapper;
import com.jamedow.laodoufang.mapper.SysDictMapper;
import com.jamedow.laodoufang.web.kxgrp.HuaYaoController;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/sys")
public class SystemController {
    static Logger logger = LoggerFactory.getLogger(HuaYaoController.class);
    private final String COMPANY_MAIL = "company_mail";
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private SysDictMapper sysDictMapper;

    @RequestMapping(value = "/mail/send", method = RequestMethod.POST)
    @ResponseBody
    public String send(String productId, Integer demand, String requirementDescription, String companyName,
                       String region, String phone, String province, String district, String betterAddress, String receiver) {

        //获取公司邮箱
        String companyMail = "";
        SysDictExample dictExample = new SysDictExample();
        dictExample.createCriteria().andTypeEqualTo(COMPANY_MAIL);
        List<SysDict> dicts = sysDictMapper.selectByExample(dictExample);
        if (dicts != null && dicts.size() != 0) {
            companyMail = dicts.get(0).getValue();
        }

        //获取国家省市名称 拼接详细地址
        StringBuilder address = new StringBuilder();
        SysAreaExample areaExample = new SysAreaExample();
        areaExample.createCriteria().andCodeEqualTo(region);
        List<SysArea> regions = sysAreaMapper.selectByExample(areaExample);
        if (regions != null && regions.size() != 0) {
            address.append(regions.get(0).getName()).append(" ");
        }
        areaExample.clear();
        areaExample.createCriteria().andCodeEqualTo(province);
        List<SysArea> provinces = sysAreaMapper.selectByExample(areaExample);
        if (provinces != null && provinces.size() != 0) {
            address.append(provinces.get(0).getName()).append(" ");
        }
        areaExample.clear();
        areaExample.createCriteria().andCodeEqualTo(region);
        List<SysArea> districts = sysAreaMapper.selectByExample(areaExample);
        if (districts != null && districts.size() != 0) {
            address.append(districts.get(0).getName()).append(" ");
        }
        address.append(betterAddress);

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("<div>").append("采购产品：").append(productId).append("</div>");
        messageBuilder.append("<div>").append("采购数量（单位:kg）：").append(demand == null ? 0 : demand).append("</div>");
        messageBuilder.append("<div>").append("需求描述：").append(requirementDescription).append("</div>");
        messageBuilder.append("<div>").append("公司／单位名称：").append(companyName).append("</div>");
        messageBuilder.append("<div>").append("联系邮箱：").append(receiver).append("</div>");
        messageBuilder.append("<div>").append("联系电话：").append(phone).append("</div>");
        messageBuilder.append("<div>").append("联系地址：").append(address.toString()).append("</div>");
        Mail mail = new Mail.Builder("smtp.qq.com", "1472541865@qq.com", "tnnfmpbgsjckbade")
                .sender("1472541865@qq.com").receiver(companyMail).name(companyName).subject(productId + "采购需求，采购方：" + companyName).message(messageBuilder.toString()).build();
        MailService.sendMail(mail);

        Mail mail1 = new Mail.Builder("smtp.qq.com", "1472541865@qq.com", "tnnfmpbgsjckbade")
                .sender("1472541865@qq.com").receiver("563150601@qq.com").name(companyName).subject(productId + "采购需求，采购方：" + companyName).message(messageBuilder.toString()).build();
        MailService.sendMail(mail1);
        return "success";
    }

    @RequestMapping(value = "/area/getChildrenByParentId", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getChildrenByParentId(String parentId) {
        SysAreaExample example = new SysAreaExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<SysArea> children = sysAreaMapper.selectByExample(example);
        return JSONArray.fromObject(children).toString();
    }


}
