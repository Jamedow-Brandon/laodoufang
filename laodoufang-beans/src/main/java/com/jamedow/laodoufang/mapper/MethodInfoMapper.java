package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.MethodInfo;
import com.jamedow.laodoufang.entity.MethodInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MethodInfoMapper {
    long countByExample(MethodInfoExample example);

    int deleteByExample(MethodInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MethodInfo record);

    int insertSelective(MethodInfo record);

    List<MethodInfo> selectByExample(MethodInfoExample example);

    MethodInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MethodInfo record, @Param("example") MethodInfoExample example);

    int updateByExample(@Param("record") MethodInfo record, @Param("example") MethodInfoExample example);

    int updateByPrimaryKeySelective(MethodInfo record);

    int updateByPrimaryKey(MethodInfo record);
}