package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.SiteAttachmentRel;
import com.jamedow.laodoufang.entity.SiteAttachmentRelExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteAttachmentRelMapper {
    long countByExample(SiteAttachmentRelExample example);

    int deleteByExample(SiteAttachmentRelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SiteAttachmentRel record);

    int insertSelective(SiteAttachmentRel record);

    List<SiteAttachmentRel> selectByExample(SiteAttachmentRelExample example);

    SiteAttachmentRel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SiteAttachmentRel record, @Param("example") SiteAttachmentRelExample example);

    int updateByExample(@Param("record") SiteAttachmentRel record, @Param("example") SiteAttachmentRelExample example);

    int updateByPrimaryKeySelective(SiteAttachmentRel record);

    int updateByPrimaryKey(SiteAttachmentRel record);
}