package com.jamedow.laodoufang.mail;

import com.jamedow.laodoufang.entity.Mail;
import com.jamedow.laodoufang.entity.MailBase;
import com.jamedow.laodoufang.entity.MailHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * @author Jamedow
 * @date 2018/3/7
 */

public class MailSend {
    private static Logger logger = LoggerFactory.getLogger(MailSend.class);

    private static String CHARSET = "UTF-8";

    /**
     * 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
     */
    Properties props = new Properties();

    /**
     * 根据参数配置，创建会话对象（为了发送邮件准备的）
     */
    Session session;

    /**
     * 创建邮件对象
     */
    MimeMessage message;

    /**
     * 根据 Session 获取邮件传输对象
     */
    Transport transport;

    Multipart multipart;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public MailSend(MailBase mailBase) throws Exception {
        try {
            // 1. 创建一封邮件

            // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.transport.protocol", mailBase.getTransportProtocol());
            // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.host", mailBase.getHost());
            // 是否进行权限校验
            props.setProperty("mail.smtp.auth", mailBase.getAuth());

            props.setProperty("mail.smtp.socketFactory.class", mailBase.getSocketFactoryClass());
            props.setProperty("mail.smtp.port", mailBase.getPort());
            props.setProperty("mail.smtp.socketFactory.port", mailBase.getSocketFactoryPort());
        } catch (Exception e) {
            logger.error("邮件基础信息初始化失败", e.getMessage(), e);
            throw new Exception("邮件基础信息初始化失败");
        }
    }

    public static void main(String[] args) {
        String[] receivers = {"530974049@qq.com"};

        MailHeader mailHeader = new MailHeader("1472541865@qq.com", "tnnfmpbgsjckbade", "1472541865@qq.com", "jamedow");

        Mail mail = new Mail.Builder().subject("未来可期").receiver(receivers).message("Wait for a girl like you!").build();

        MailBase mailBase = new MailBase();
        mailBase.setHost("smtp.qq.com");
        mailBase.setAuth("true");
        mailBase.setPort("465");
        mailBase.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");
        mailBase.setSocketFactoryPort("465");
        mailBase.setTransportProtocol("smtp");

        try {
            new MailSend(mailBase).setMailHeader(mailHeader).setMailContent(mail)
                    .addAttachment(new File("/Users/brandon/Documents/人工智障.png")).sendEmail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public MailSend setMailHeader(MailHeader mailheader) {
        try {
            username = mailheader.getUsername();
            password = mailheader.getPassword();

            // 根据参数配置，创建会话对象（为了发送邮件准备的）
            session = Session.getInstance(props);
            //设为true，可以看到发送邮件的整个过程
            session.setDebug(true);
            // 创建邮件对象
            message = new MimeMessage(session);
            // 创建邮件内容
            multipart = new MimeMultipart();
            /*
             * 也可以根据已有的eml邮件文件创建 MimeMessage 对象
             * MimeMessage message = new MimeMessage(session, new FileInputStream("MyEmail.eml"));
             */

            // 2. From: 发件人
            //    其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
            //    真正要发送时, 邮箱必须是真实有效的邮箱。
            message.setFrom(new InternetAddress(mailheader.getSender(), mailheader.getName(), CHARSET));
        } catch (Exception e) {
            logger.error("邮件头信息初始化失败", e.getMessage(), e);
        }
        return this;
    }

    public MailSend setMailContent(Mail mail) throws Exception {
        try {
            // 1. Subject: 邮件主题
            message.setSubject(mail.getSubject(), CHARSET);

            // 2. To: 收件人
            String[] receivers = mail.getTo();
            for (int i = 0, count = receivers.length; i < count; i++) {
                if (i == 0) {
                    message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receivers[i], "USER_CC", CHARSET));
                } else {
                    message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receivers[i], "USER_DD", CHARSET));
                }
            }

            if (!ObjectUtils.isEmpty(mail.getCc())) {
                for (String cc : mail.getCc()) {
                    message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(cc, "USER_EE", CHARSET));
                }
            }

            MimeBodyPart content = new MimeBodyPart();
            content.setText(mail.getMessage());
            multipart.addBodyPart(content);


        } catch (Exception e) {
            logger.error("邮件内容初始化失败", e.getMessage(), e);
            throw new Exception("邮件内容初始化失败");
        }
        return this;
    }

    public MailSend addTO(String[] receivers) throws Exception {
        try {
            for (String receiver : receivers) {
                message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver, "USER_DD", CHARSET));
            }
        } catch (Exception e) {
            logger.error("添加收件人失败", e.getMessage(), e);
            throw new Exception("添加收件人失败");
        }
        return this;
    }

    public MailSend addCC(String[] receivers) throws Exception {
        try {
            for (String receiver : receivers) {
                message.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(receiver, "USER_DD", CHARSET));
            }
        } catch (Exception e) {
            logger.error("添加抄送人失败", e.getMessage(), e);
            throw new Exception("添加抄送人失败");
        }
        return this;
    }

    public MailSend addBCC(String[] receivers) throws Exception {
        try {
            for (String receiver : receivers) {
                message.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(receiver, "USER_DD", CHARSET));
            }
        } catch (Exception e) {
            logger.error("添加密送人失败", e.getMessage(), e);
            throw new Exception("添加密送人失败");
        }
        return this;
    }

    public MailSend addAttachment(File file) throws Exception {
        try {
            MimeBodyPart attachment = new MimeBodyPart();
            FileDataSource fileSource = new FileDataSource(file);
            attachment.setDataHandler(new DataHandler(fileSource));
            attachment.setFileName(fileSource.getName());
            multipart.addBodyPart(attachment);
        } catch (Exception e) {
            logger.error("添加附件失败", e.getMessage(), e);
            throw new Exception("添加附件失败");
        }
        return this;
    }

    public MailSend sendEmail() throws Exception {
        try {
            // 6. 设置显示的发件时间
            message.setSentDate(new Date());
            //设置正文内容
            message.setContent(multipart);

            // 7. 保存前面的设置
            message.saveChanges();
            // 4. 根据 Session 获取邮件传输对象
            transport = session.getTransport();
            transport.connect(username, password);
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();
        } catch (Exception e) {
            logger.error("邮件发送失败", e.getMessage(), e);
            throw new Exception("邮件发送失败");
        }
        return this;
    }

}
