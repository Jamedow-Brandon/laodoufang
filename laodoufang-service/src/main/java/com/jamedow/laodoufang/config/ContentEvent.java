package com.jamedow.laodoufang.config;

import org.springframework.context.ApplicationEvent;

public class ContentEvent extends ApplicationEvent {
    public ContentEvent() {
        super("test");
    }
}