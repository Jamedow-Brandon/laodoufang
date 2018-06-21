package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.MethodConfigRel;
import com.jamedow.laodoufang.entity.MethodConfigRelExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MethodConfigRelMapper {
    long countByExample(MethodConfigRelExample example);

    int deleteByExample(MethodConfigRelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MethodConfigRel record);

    int insertSelective(MethodConfigRel record);

    List<MethodConfigRel> selectByExample(MethodConfigRelExample example);

    MethodConfigRel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MethodConfigRel record, @Param("example") MethodConfigRelExample example);

    int updateByExample(@Param("record") MethodConfigRel record, @Param("example") MethodConfigRelExample example);

    int updateByPrimaryKeySelective(MethodConfigRel record);

    int updateByPrimaryKey(MethodConfigRel record);
}