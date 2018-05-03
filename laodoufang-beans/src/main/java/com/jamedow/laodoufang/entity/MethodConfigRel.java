package com.jamedow.laodoufang.entity;

public class MethodConfigRel {
    private Long id;

    private Long confidId;

    private Long methodId;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConfidId() {
        return confidId;
    }

    public void setConfidId(Long confidId) {
        this.confidId = confidId;
    }

    public Long getMethodId() {
        return methodId;
    }

    public void setMethodId(Long methodId) {
        this.methodId = methodId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}