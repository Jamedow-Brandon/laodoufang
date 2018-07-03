package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.protegeDataRel;
import com.jamedow.laodoufang.entity.protegeDataRelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface protegeDataRelMapper {
    long countByExample(protegeDataRelExample example);

    int deleteByExample(protegeDataRelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(protegeDataRel record);

    int insertSelective(protegeDataRel record);

    List<protegeDataRel> selectByExample(protegeDataRelExample example);

    protegeDataRel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") protegeDataRel record, @Param("example") protegeDataRelExample example);

    int updateByExample(@Param("record") protegeDataRel record, @Param("example") protegeDataRelExample example);

    int updateByPrimaryKeySelective(protegeDataRel record);

    int updateByPrimaryKey(protegeDataRel record);
}