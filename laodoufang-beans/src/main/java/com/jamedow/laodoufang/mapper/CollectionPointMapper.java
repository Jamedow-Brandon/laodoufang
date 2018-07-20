package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.CollectionPoint;
import com.jamedow.laodoufang.entity.CollectionPointExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionPointMapper {
    long countByExample(CollectionPointExample example);

    int deleteByExample(CollectionPointExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CollectionPoint record);

    int insertSelective(CollectionPoint record);

    List<CollectionPoint> selectByExample(CollectionPointExample example);

    CollectionPoint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CollectionPoint record, @Param("example") CollectionPointExample example);

    int updateByExample(@Param("record") CollectionPoint record, @Param("example") CollectionPointExample example);

    int updateByPrimaryKeySelective(CollectionPoint record);

    int updateByPrimaryKey(CollectionPoint record);
}