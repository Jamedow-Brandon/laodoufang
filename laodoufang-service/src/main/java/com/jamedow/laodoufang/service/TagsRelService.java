package com.jamedow.laodoufang.service;

import com.jamedow.laodoufang.entity.TagsRel;
import com.jamedow.laodoufang.entity.TagsRelExample;
import com.jamedow.laodoufang.mapper.TagsRelMapper;
import com.jamedow.laodoufang.mapper.TagsRelMapperEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yoyo on 2017/7/13.
 */
@Service
public class TagsRelService {

    @Autowired
    private TagsRelMapper tagsRelMapper;

    @Autowired
    private TagsRelMapperEx tagsRelMapperEx;

    public List<TagsRel> queryTagsRel(TagsRel tagsRel) {

        TagsRelExample example = new TagsRelExample();
        TagsRelExample.Criteria criteria = example.createCriteria();

        if (tagsRel != null) {

            if (null != tagsRel.getParentId()) {
                criteria.andParentIdEqualTo(tagsRel.getParentId());
            }
            if (null != tagsRel.getTagId()) {
                criteria.andTagIdEqualTo(tagsRel.getTagId());
            }

        }
        return tagsRelMapper.selectByExample(example);
    }

    public int deleteTagsRel(int tagsRelId) {

        return tagsRelMapper.deleteByPrimaryKey(tagsRelId);
    }

    public int save(TagsRel tagsRel) {

        if (null != tagsRel.getId())
            return tagsRelMapper.updateByPrimaryKeySelective(tagsRel);
        return tagsRelMapper.insert(tagsRel);


    }

    public int deleteRelByTagId(int tagsId) {

        TagsRelExample example = new TagsRelExample();
        TagsRelExample.Criteria criteria = example.createCriteria();
        criteria.andTagIdEqualTo(tagsId);
        return tagsRelMapper.deleteByExample(example);
    }
}
