package com.jamedow.laodoufang.entity;

import lombok.Data;

@Data
public class MailHeader {
    /**
     * 账号
     */
    private final String username;

    /**
     * 密码
     */
    private final String password;

    /**
     * 发件人的邮箱
     */
    private final String sender;

    /**
     * 发件人昵称
     */
    private final String name;
}
