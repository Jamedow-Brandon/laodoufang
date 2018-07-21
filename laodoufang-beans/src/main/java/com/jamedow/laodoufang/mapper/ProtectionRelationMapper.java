package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.ProtectionRelation;
import com.jamedow.laodoufang.entity.ProtectionRelationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtectionRelationMapper {
    long countByExample(ProtectionRelationExample example);

    int deleteByExample(ProtectionRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProtectionRelation record);

    int insertSelective(ProtectionRelation record);

    List<ProtectionRelation> selectByExample(ProtectionRelationExample example);

    ProtectionRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProtectionRelation record, @Param("example") ProtectionRelationExample example);

    int updateByExample(@Param("record") ProtectionRelation record, @Param("example") ProtectionRelationExample example);

    int updateByPrimaryKeySelective(ProtectionRelation record);

    int updateByPrimaryKey(ProtectionRelation record);
}