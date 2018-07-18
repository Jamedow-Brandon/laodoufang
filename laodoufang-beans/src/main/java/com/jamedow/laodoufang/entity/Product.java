package com.jamedow.laodoufang.entity;

public class Product {
    private Integer id;

    private String name;

    private String seoKeywords;

    private String enName;

    private String enSeoKeywords;

    private String code;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getEnSeoKeywords() {
        return enSeoKeywords;
    }

    public void setEnSeoKeywords(String enSeoKeywords) {
        this.enSeoKeywords = enSeoKeywords;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}