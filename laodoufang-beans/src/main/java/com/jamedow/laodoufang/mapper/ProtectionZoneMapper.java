package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.ProtectionZone;
import com.jamedow.laodoufang.entity.ProtectionZoneExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtectionZoneMapper {
    long countByExample(ProtectionZoneExample example);

    int deleteByExample(ProtectionZoneExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProtectionZone record);

    int insertSelective(ProtectionZone record);

    List<ProtectionZone> selectByExample(ProtectionZoneExample example);

    ProtectionZone selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProtectionZone record, @Param("example") ProtectionZoneExample example);

    int updateByExample(@Param("record") ProtectionZone record, @Param("example") ProtectionZoneExample example);

    int updateByPrimaryKeySelective(ProtectionZone record);

    int updateByPrimaryKey(ProtectionZone record);
}