package com.jamedow.laodoufang.entity;

import lombok.Data;

/**
 * @author Jamedow
 */
@Data
public class BaseAttachment {
    private Integer id;

    private String resourceId;

    private String resourceType;

    private Integer bizType;

    private String name;

    private String suffix;

    private long size;

    private String remotePath;

    private String remark;
}