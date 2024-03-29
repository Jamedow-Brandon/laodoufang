package com.jamedow.laodoufang.service;

import com.jamedow.laodoufang.entity.Tags;
import com.jamedow.laodoufang.entity.TagsExample;
import com.jamedow.laodoufang.entity.TagsRel;
import com.jamedow.laodoufang.entity.TagsRelExample;
import com.jamedow.laodoufang.entity.ex.TagsExt;
import com.jamedow.laodoufang.mapper.TagsMapper;
import com.jamedow.laodoufang.mapper.TagsMapperEx;
import com.jamedow.laodoufang.mapper.TagsRelMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ydy on 2017/2/15.
 */
@Service
public class TagsService {


    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private TagsRelMapper tagsRelMapper;

    @Autowired
    private TagsMapperEx tagsMapperEx;

    @Autowired
    private TagsRelService tagsRelService;

    public int saveTag(Tags tags) {
        if (null != tags.getId()) {
            return tagsMapper.updateByPrimaryKeySelective(tags);
        }
        return tagsMapper.insert(tags);
    }


    public List<Tags> getBrothersByTagsId(Integer tagsId) {
        List<Tags> brothersTags = new ArrayList<>();
        TagsRelExample tagsRelExample = new TagsRelExample();
        tagsRelExample.createCriteria().andTagIdEqualTo(tagsId);
        List<TagsRel> tagsRels = tagsRelMapper.selectByExample(tagsRelExample);

        if (tagsRels != null && tagsRels.size() != 0) {
            tagsRelExample.clear();
            tagsRelExample.createCriteria().andParentIdEqualTo(tagsRels.get(0).getParentId());
            List<TagsRel> brothersTagsRels = tagsRelMapper.selectByExample(tagsRelExample);

            List<Integer> tagsIds = new ArrayList<>();
            for (TagsRel tagsRel : brothersTagsRels) {
                tagsIds.add(tagsRel.getTagId());
            }

            TagsExample tagsExample = new TagsExample();
            tagsExample.createCriteria().andIdIn(tagsIds);
            brothersTags = tagsMapper.selectByExample(tagsExample);
        }
        return brothersTags;
    }

    public List<TagsExt> queryClassifyAndChildren() {

        List<Tags> classifyList = tagsMapperEx.queryClassify();
        List<TagsExt> tagsExtList = new ArrayList<TagsExt>();
        for (Tags t : classifyList) {

            List<Tags> tagsList = tagsMapperEx.queryTagByClassify(t.getId());
            TagsExt tagsExt = new TagsExt();
            tagsExt.setId(t.getId());
            tagsExt.setName(t.getName());
            tagsExt.setChildrenTagsList(tagsList);
            tagsExtList.add(tagsExt);
        }
        return tagsExtList;
    }

    public List<Tags> getParentsByTags(int tagsId) {
        List<Tags> classifyList = tagsMapperEx.queryParentByTags(tagsId);
        return classifyList;
    }

    public List<Tags> queryClassify() {

        List<Tags> classifyList = tagsMapperEx.queryClassify();
        return classifyList;
    }

    public List<Tags> queryTagsByClassify(int classifyId) {

        List<Tags> tagsList = tagsMapperEx.queryTagByClassify(classifyId);
        return tagsList;
    }

    public String deleteTags(int tagsId) {

        List<Tags> tagsList = queryTagsByClassify(tagsId);
        //没有子节点才可以删除
        if (tagsList.size() == 0) {

            int result = tagsMapper.deleteByPrimaryKey(tagsId);
            if (result != 0) {

                TagsRelExample example = new TagsRelExample();
                TagsRelExample.Criteria criteria = example.createCriteria();
                criteria.andTagIdEqualTo(tagsId);
                if (tagsRelMapper.deleteByExample(example) != 0)
                    return "删除成功";
            }
            return "删除失败";
        }
        return "存在子节点,删除失败";
    }


    public List<Tags> getTagsByParentId(Integer parentId) {

        TagsRelExample tagsRelExample = new TagsRelExample();
        tagsRelExample.createCriteria().andParentIdEqualTo(parentId);
        List<TagsRel> tagsRels = tagsRelMapper.selectByExample(tagsRelExample);

        List<Integer> tagsIds = new ArrayList<>();
        for (TagsRel tagsRel : tagsRels) {
            tagsIds.add(tagsRel.getTagId());
        }

        TagsExample tagsExample = new TagsExample();
        tagsExample.createCriteria().andIdIn(tagsIds);
        return tagsMapper.selectByExample(tagsExample);
    }

    public Tags saveClassify(Tags tags) {

        Tags oldTags = new Tags();
        oldTags.setId(tags.getId());
        List<Tags> tagsList = queryTags(tags);//检查是否有重名
        if (tagsList.size() == 0) {

            tags.setIsLeaf("0");
            int result = saveTag(tags);//保存成功返回id
            if (result != 0) {

                if (null != oldTags.getId())//修改则不添加关系表
                    return tags;
                TagsRel tagsRel = new TagsRel();
                tagsRel.setTagId(tags.getId());
                tagsRel.setParentId(0);
                result = tagsRelService.save(tagsRel);
                if (result != 0)
                    return tags;
            }
            tags.setId(0);
            tags.setName("保存失败");//保存失败
            return tags;

        }
        tags.setName("名称重复");
        tags.setId(0);//名称重复
        return tags;
    }

    public List<Tags> queryTags(Tags tags) {
        TagsExample example = new TagsExample();
        TagsExample.Criteria criteria = example.createCriteria();

        if (tags != null) {

            if (StringUtils.isNotBlank(tags.getIsLeaf())) {
                criteria.andIsLeafEqualTo(tags.getIsLeaf());
            }
            if (StringUtils.isNotBlank(tags.getName())) {
                criteria.andNameEqualTo(tags.getName());
            }

        }
        return tagsMapper.selectByExample(example);
    }

    public List<Tags> getLeaves() {
        TagsExample example = new TagsExample();
        example.createCriteria().andIsLeafEqualTo("1");
        return tagsMapper.selectByExample(example);
    }
}
