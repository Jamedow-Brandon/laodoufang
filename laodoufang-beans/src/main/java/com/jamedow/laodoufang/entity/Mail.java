package com.jamedow.laodoufang.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jamedow
 * @date 2018/3/7
 */
@Data
public class Mail implements Serializable {
    private String host; // 服务器地址

    private String sender; // 发件人的邮箱

    private String receiver; // 收件人的邮箱

    private String name; // 发件人昵称

    private String username; // 账号

    private String password; // 密码

    private String subject; // 主题

    private String message; // 信息(支持HTML)
}