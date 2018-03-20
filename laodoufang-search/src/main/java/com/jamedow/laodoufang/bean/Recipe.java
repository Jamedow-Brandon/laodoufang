package com.jamedow.laodoufang.bean;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jamedow
 */
@Data
@Document(indexName = "laodoufang", type = "recipe", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;

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