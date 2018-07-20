package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.PrewarningSetting;
import com.jamedow.laodoufang.entity.PrewarningSettingExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrewarningSettingMapper {
    long countByExample(PrewarningSettingExample example);

    int deleteByExample(PrewarningSettingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PrewarningSetting record);

    int insertSelective(PrewarningSetting record);

    List<PrewarningSetting> selectByExample(PrewarningSettingExample example);

    PrewarningSetting selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PrewarningSetting record, @Param("example") PrewarningSettingExample example);

    int updateByExample(@Param("record") PrewarningSetting record, @Param("example") PrewarningSettingExample example);

    int updateByPrimaryKeySelective(PrewarningSetting record);

    int updateByPrimaryKey(PrewarningSetting record);
}