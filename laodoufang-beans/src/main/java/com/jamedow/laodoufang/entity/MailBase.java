package com.jamedow.laodoufang.entity;

import lombok.Data;

@Data
public class MailBase {

    /**
     * 使用的协议（JavaMail规范要求）
     */
    private String transportProtocol;
    /**
     * 发件人的邮箱的 SMTP 服务器地址
     */
    private String host;
    /**
     * 需要请求认证
     */
    private String auth;
    private String socketFactoryClass;
    private String port;
    private String socketFactoryPort;
}
