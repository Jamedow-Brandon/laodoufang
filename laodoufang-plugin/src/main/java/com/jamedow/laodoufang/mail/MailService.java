package com.jamedow.laodoufang.mail;

import com.jamedow.laodoufang.entity.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author Jamedow
 * @date 2018/3/7
 */

public class MailService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String CHARSET = "UTF-8";

    public void sendMail(Mail mail) {
        try {
            // 1. 创建一封邮件
            // 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
            Properties props = new Properties();
            // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.transport.protocol", "smtp");
            // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.host", mail.getHost());
            // 需要请求认证
            props.setProperty("mail.smtp.auth", "true");

            // 根据参数配置，创建会话对象（为了发送邮件准备的）
            Session session = Session.getInstance(props);
            // 创建邮件对象
            MimeMessage message = new MimeMessage(session);

        /*
         * 也可以根据已有的eml邮件文件创建 MimeMessage 对象
         * MimeMessage message = new MimeMessage(session, new FileInputStream("MyEmail.eml"));
         */

            // 2. From: 发件人
            //    其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
            //    真正要发送时, 邮箱必须是真实有效的邮箱。
            message.setFrom(new InternetAddress(mail.getSender(), mail.getName(), CHARSET));

            // 3. To: 收件人
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mail.getReceiver(), "USER_CC", CHARSET));
//            //    To: 增加收件人（可选）
//            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("dd@receive.com", "USER_DD", CHARSET));
//            //    Cc: 抄送（可选）
//            message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("ee@receive.com", "USER_EE", CHARSET));
//            //    Bcc: 密送（可选）
//            message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", CHARSET));

            // 4. Subject: 邮件主题
            message.setSubject(mail.getSubject(), CHARSET);

            // 5. Content: 邮件正文（可以使用html标签）
            message.setContent(mail.getMessage(), "text/html;charset=UTF-8");

            // 6. 设置显示的发件时间
            message.setSentDate(new Date());

            // 7. 保存前面的设置
            message.saveChanges();

            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            transport.connect(mail.getSender(), mail.getPassword());

            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 7. 关闭连接
            transport.close();
        } catch (Exception e) {
            logger.error("邮件发送失败", e.getMessage(), e);
        }

    }
}
