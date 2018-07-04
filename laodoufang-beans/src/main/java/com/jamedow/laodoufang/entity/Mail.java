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
     * 主题
     */
    private final String subject;
    /**
     * 收件人的邮箱
     */
    private final String[] to;
    /**
     * 收件人的邮箱
     */
    private final String[] cc;
    /**
     * 信息(支持HTML)
     */
    private final String message;

    private Mail(Builder builder) {
        to = builder.to;
        cc = builder.cc;
        subject = builder.subject;
        message = builder.message;
    }

    public static class Builder {
        private String subject;

        private String[] to;
        private String[] cc;

        private String message;

        public Builder() {
        }

        public Builder receiver(String[] val) {
            to = val;
            return this;
        }

        public Builder cc(String[] val) {
            cc = val;
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

        public Mail build() {
            return new Mail(this);
        }
    }
}