package com.jamedow.laodoufang.mapper;

import com.jamedow.laodoufang.entity.ProductSeoKeywords;
import com.jamedow.laodoufang.entity.ProductSeoKeywordsExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductSeoKeywordsMapper {
    long countByExample(ProductSeoKeywordsExample example);

    int deleteByExample(ProductSeoKeywordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductSeoKeywords record);

    int insertSelective(ProductSeoKeywords record);

    List<ProductSeoKeywords> selectByExample(ProductSeoKeywordsExample example);

    ProductSeoKeywords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductSeoKeywords record, @Param("example") ProductSeoKeywordsExample example);

    int updateByExample(@Param("record") ProductSeoKeywords record, @Param("example") ProductSeoKeywordsExample example);

    int updateByPrimaryKeySelective(ProductSeoKeywords record);

    int updateByPrimaryKey(ProductSeoKeywords record);
}