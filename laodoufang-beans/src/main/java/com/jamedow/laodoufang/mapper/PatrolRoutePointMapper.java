package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.PatrolRoutePoint;
import com.jamedow.laodoufang.entity.PatrolRoutePointExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatrolRoutePointMapper {
    long countByExample(PatrolRoutePointExample example);

    int deleteByExample(PatrolRoutePointExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PatrolRoutePoint record);

    int insertSelective(PatrolRoutePoint record);

    List<PatrolRoutePoint> selectByExample(PatrolRoutePointExample example);

    PatrolRoutePoint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PatrolRoutePoint record, @Param("example") PatrolRoutePointExample example);

    int updateByExample(@Param("record") PatrolRoutePoint record, @Param("example") PatrolRoutePointExample example);

    int updateByPrimaryKeySelective(PatrolRoutePoint record);

    int updateByPrimaryKey(PatrolRoutePoint record);
}