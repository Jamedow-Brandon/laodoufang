package com.jamedow.laodoufang.mail;
import com.jamedow.laodoufang.entity.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 邮件管理器
 * java 实现邮件的发送， 抄送及多附件
 * @author
 * @version 1.0
 * @created
 */
public class EmailManager {

    public final static Logger logger= LoggerFactory.getLogger(EmailManager.class);

    public final static String CHARSET="UTF-8";

    public static String username = "service@zhuxiongxian.cc"; // 服务邮箱(from邮箱)
    public static String password = "yourpassword"; // 邮箱密码
    public static String senderNick = "XX科技";   // 发件人昵称

    private Properties props; // 系统属性
    private Session session; // 邮件会话对象
    private MimeMessage mimeMsg; // MIME邮件对象
    private Multipart mp;   // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

    private static EmailManager instance = null;

    public EmailManager() {
        props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        props.put("mail.smtp.port", "25");
        props.put("username", username);
        props.put("password", password);
        // 建立会话
        session = Session.getDefaultInstance(props);
        session.setDebug(false);
    }

    public static EmailManager getInstance() {
        if(instance==null){
            synchronized (EmailManager.class){
                if (instance == null) {
                    instance = new EmailManager();
                }
            }

        }

        return instance;
    }

    /**
     * 发送邮件
     * @param mail
     * @return
     */
    public boolean sendMail(Mail mail) {
        //附件列表
        String []fileList=mail.getFileList();
        //抄送人
        String []copyto=mail.getCopyTo();
        //收件人
        String []to=mail.getReceiver();
        boolean success = true;
        try {
            mimeMsg = new MimeMessage(session);
            mp = new MimeMultipart();

            // 自定义发件人昵称
            String nick = "";
            try {
                nick = MimeUtility.encodeText(senderNick);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // 设置发件人
            mimeMsg.setFrom(new InternetAddress(mail.getSender(), mail.getName(),CHARSET));

            // 设置收件人
            if (to != null && to.length > 0) {
                String toListStr = getMailList(to);
                mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toListStr));
            }
            // 设置抄送人
            if (copyto != null && copyto.length > 0) {
                String ccListStr = getMailList(copyto);
                mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccListStr));
            }
            // 设置主题
            mimeMsg.setSubject(mail.getSubject(),CHARSET);
            // 设置正文
            BodyPart bp = new MimeBodyPart();
            bp.setContent(mail.getMessage(), "text/html;charset=utf-8");
            mp.addBodyPart(bp);

            // 设置附件
            if (fileList != null && fileList.length > 0) {
                for (int i = 0; i < fileList.length; i++) {
                    bp = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(fileList[i]);
                    bp.setDataHandler(new DataHandler(fds));
                    bp.setFileName(MimeUtility.encodeText(fds.getName(), CHARSET, "B"));
                    mp.addBodyPart(bp);
                }
            }
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            // 发送邮件
            if (props.get("mail.smtp.auth").equals("true")) {
                Transport transport = session.getTransport("smtp");
                transport.connect((String) props.get("mail.smtp.host"), (String) props.get("username"), (String) props.get("password"));
                transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
                transport.close();
            } else {
                Transport.send(mimeMsg);
            }
            System.out.println("邮件发送成功");
        } catch (MessagingException e) {
            logger.error("邮件发送失败", e.getMessage(), e);
            success = false;
        } catch (UnsupportedEncodingException e) {
            logger.error("邮件发送失败", e.getMessage(), e);
            success = false;
        }
        return success;
    }


    public String getMailList(String[] mailArray) {
        StringBuffer toList = new StringBuffer();
        int length = mailArray.length;
        if (mailArray != null && length < 2) {
            toList.append(mailArray[0]);
        } else {
            for (int i = 0; i < length; i++) {
                toList.append(mailArray[i]);
                if (i != (length - 1)) {
                    toList.append(",");
                }

            }
        }
        return toList.toString();
    }

    public static void main(String[] args) {
        String from = username;
        String[] to = {"10086@qq.com", "xx@zhuxiongxian.cc"};
        String[] copyto = {"123456@163.com"};
        String subject = "测试一下";
        String content = "这是邮件内容，仅仅是测试，不需要回复.";
        String[] fileList = new String[3];
        fileList[0] = "d:/zxing.png";
        fileList[1] = "d:/urls.txt";
        fileList[2] = "d:/surname.txt";
//        EmailManager.getInstance().sendMail(from, to, copyto, subject, content, fileList);
    }
}