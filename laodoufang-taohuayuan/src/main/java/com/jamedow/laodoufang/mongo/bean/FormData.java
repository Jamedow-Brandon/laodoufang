package com.jamedow.laodoufang.mongo.bean;

import org.springframework.data.annotation.Id;

import java.util.Map;

public class FormData {

    @Id
    private Long id;
    private Map<String, Object> data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
