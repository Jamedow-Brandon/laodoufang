package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.Shelter;
import com.jamedow.laodoufang.entity.ShelterExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterMapper {
    long countByExample(ShelterExample example);

    int deleteByExample(ShelterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Shelter record);

    int insertSelective(Shelter record);

    List<Shelter> selectByExample(ShelterExample example);

    Shelter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Shelter record, @Param("example") ShelterExample example);

    int updateByExample(@Param("record") Shelter record, @Param("example") ShelterExample example);

    int updateByPrimaryKeySelective(Shelter record);

    int updateByPrimaryKey(Shelter record);
}