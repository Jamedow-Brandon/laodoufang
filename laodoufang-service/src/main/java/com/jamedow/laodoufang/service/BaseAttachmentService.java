package com.jamedow.laodoufang.service;

import com.jamedow.laodoufang.entity.BaseAttachment;
import com.jamedow.laodoufang.mapper.BaseAttachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 365 on 2016/12/12 0012.
 */
@Service
public class BaseAttachmentService {
    @Autowired
    private BaseAttachmentMapper attachmentMapper;

    public int saveBaseAttachment(BaseAttachment baseAttachment) {
        return attachmentMapper.insert(baseAttachment);
    }

    public int deleteBaseAttachment(Integer id) {
        return attachmentMapper.deleteByPrimaryKey(id);
    }
}
