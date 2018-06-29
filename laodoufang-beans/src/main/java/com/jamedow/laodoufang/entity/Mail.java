package com.jamedow.laodoufang.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jamedow
 * @date 2018/3/7
 */
@Data
public class Mail implements Serializable {
    /**
     * 服务器地址
     */
    private final String host;

    /**
     * 发件人的邮箱
     */
    private final String sender;

    /**
     * 收件人的邮箱
     */
    private final String[] receiver;

    /**
     * 发件人昵称
     */
    private final String name;

    /**
     * 账号
     */
    private final String username;

    /**
     * 密码
     */
    private final String password;

    /**
     * 主题
     */
    private final String subject;

    /**
     * 附件列表
     */
    private String []fileList;

    /**
     * 抄送人
     */
    private String []copyTo;



    /**
     * 信息(支持HTML)
     */
    private final String message;

    private Mail(Builder builder) {
        host = builder.host;
        sender = builder.sender;
        receiver = builder.receiver;
        name = builder.name;
        username = builder.username;
        password = builder.password;
        subject = builder.subject;
        message = builder.message;
        fileList=builder.fileList;
        copyTo=builder.copyTo;
    }

    public static class Builder {
        private final String host;
        private final String username;
        private final String password;

        private String sender;
        private String []receiver;
        private String name;

        private String subject;
        private String message;

        private String []fileList;
        private String []copyTo;

        public Builder(String host, String username, String password) {
            this.host = host;
            this.username = username;
            this.password = password;
        }

        public Builder sender(String val) {
            sender = val;
            return this;
        }

        public Builder receiver(String []val) {
            receiver = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }


        public Builder subject(String val) {
            subject = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }
        public Builder copyTo(String []val){
            copyTo=val;
            return this;
        }
        public Builder fileList(String []val){
            fileList=val;
            return  this;
        }

        public Mail build() {
            return new Mail(this);
        }
    }
}