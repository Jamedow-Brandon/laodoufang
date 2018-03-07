package com.jamedow.laodoufang.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Jamedow
 */
@Data
public class Recipe {
    private Integer id;

    private String name;

    private String intro;

    private Date createTime;

    private String linkUrl;

    private String imgUrl;

    private Integer category;

    private String tags;

    private Integer voteUp;

    private Integer voteDown;

    private String isOfficial;

    private Integer userId;

    private Integer trafficVolume;

    private String searchDocumentId;

    private String ingredient;

    private String burdening;

    private String detail;
}