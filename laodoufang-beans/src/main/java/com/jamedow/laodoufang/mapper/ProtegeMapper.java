package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.Protege;
import com.jamedow.laodoufang.entity.ProtegeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProtegeMapper {
    long countByExample(ProtegeExample example);

    int deleteByExample(ProtegeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Protege record);

    int insertSelective(Protege record);

    List<Protege> selectByExample(ProtegeExample example);

    Protege selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Protege record, @Param("example") ProtegeExample example);

    int updateByExample(@Param("record") Protege record, @Param("example") ProtegeExample example);

    int updateByPrimaryKeySelective(Protege record);

    int updateByPrimaryKey(Protege record);
}