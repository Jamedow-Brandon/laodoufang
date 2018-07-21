package com.jamedow.laodoufang.entity;

import java.math.BigDecimal;

public class PatrolRoutePoint {
    private Integer id;

    private Integer patrolRouteId;

    private BigDecimal longitude;

    private BigDecimal latitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatrolRouteId() {
        return patrolRouteId;
    }

    public void setPatrolRouteId(Integer patrolRouteId) {
        this.patrolRouteId = patrolRouteId;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}