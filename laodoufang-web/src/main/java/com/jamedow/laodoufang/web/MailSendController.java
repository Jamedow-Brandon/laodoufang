package com.jamedow.laodoufang.web;

import com.jamedow.laodoufang.entity.Mail;
import com.jamedow.laodoufang.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jamedow
 * @date 2018/3/7
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("mail")
public class MailSendController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String mail() {
        return "mail/form";
    }

    @RequestMapping(value = "send", method = RequestMethod.GET)
    @ResponseBody
    public String send(String sender, String name, String message) {
        Mail mail = new Mail.Builder("http://mail.qq.com", "652972575@qq.com", "Zhangliang@520")
                .sender(sender).name(name).message(message).build();
        MailService.sendMail(mail);
        return "success";
    }
}
