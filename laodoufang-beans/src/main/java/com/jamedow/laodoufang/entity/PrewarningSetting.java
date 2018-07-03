package com.jamedow.laodoufang.entity;

import java.math.BigDecimal;

public class PrewarningSetting {
    private Long id;

    private String code;

    private BigDecimal value;

    private Boolean compareWay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Boolean getCompareWay() {
        return compareWay;
    }

    public void setCompareWay(Boolean compareWay) {
        this.compareWay = compareWay;
    }
}