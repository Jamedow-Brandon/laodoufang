package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.PatrolRoute;
import com.jamedow.laodoufang.entity.PatrolRouteExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatrolRouteMapper {
    long countByExample(PatrolRouteExample example);

    int deleteByExample(PatrolRouteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PatrolRoute record);

    int insertSelective(PatrolRoute record);

    List<PatrolRoute> selectByExample(PatrolRouteExample example);

    PatrolRoute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PatrolRoute record, @Param("example") PatrolRouteExample example);

    int updateByExample(@Param("record") PatrolRoute record, @Param("example") PatrolRouteExample example);

    int updateByPrimaryKeySelective(PatrolRoute record);

    int updateByPrimaryKey(PatrolRoute record);
}