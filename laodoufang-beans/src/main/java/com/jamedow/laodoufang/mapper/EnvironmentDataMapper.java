package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.EnvironmentData;
import com.jamedow.laodoufang.entity.EnvironmentDataExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnvironmentDataMapper {
    long countByExample(EnvironmentDataExample example);

    int deleteByExample(EnvironmentDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EnvironmentData record);

    int insertSelective(EnvironmentData record);

    List<EnvironmentData> selectByExample(EnvironmentDataExample example);

    EnvironmentData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EnvironmentData record, @Param("example") EnvironmentDataExample example);

    int updateByExample(@Param("record") EnvironmentData record, @Param("example") EnvironmentDataExample example);

    int updateByPrimaryKeySelective(EnvironmentData record);

    int updateByPrimaryKey(EnvironmentData record);
}