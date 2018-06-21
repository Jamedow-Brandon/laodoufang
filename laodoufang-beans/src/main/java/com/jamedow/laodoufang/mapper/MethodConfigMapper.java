package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.MethodConfig;
import com.jamedow.laodoufang.entity.MethodConfigExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MethodConfigMapper {
    long countByExample(MethodConfigExample example);

    int deleteByExample(MethodConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MethodConfig record);

    int insertSelective(MethodConfig record);

    List<MethodConfig> selectByExample(MethodConfigExample example);

    MethodConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MethodConfig record, @Param("example") MethodConfigExample example);

    int updateByExample(@Param("record") MethodConfig record, @Param("example") MethodConfigExample example);

    int updateByPrimaryKeySelective(MethodConfig record);

    int updateByPrimaryKey(MethodConfig record);
}