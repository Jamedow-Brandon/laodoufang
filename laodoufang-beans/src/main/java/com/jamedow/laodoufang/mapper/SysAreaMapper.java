package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.SysArea;
import com.jamedow.laodoufang.entity.SysAreaExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysAreaMapper {
    long countByExample(SysAreaExample example);

    int deleteByExample(SysAreaExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysArea record);

    int insertSelective(SysArea record);

    List<SysArea> selectByExample(SysAreaExample example);

    SysArea selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysArea record, @Param("example") SysAreaExample example);

    int updateByExample(@Param("record") SysArea record, @Param("example") SysAreaExample example);

    int updateByPrimaryKeySelective(SysArea record);

    int updateByPrimaryKey(SysArea record);
}