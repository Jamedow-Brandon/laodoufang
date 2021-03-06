package com.jamedow.laodoufang.web;

import com.jamedow.laodoufang.entity.BaseAttachment;
import com.jamedow.laodoufang.entity.BaseAttachmentExample;
import com.jamedow.laodoufang.mapper.BaseAttachmentMapper;
import com.jamedow.laodoufang.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/25.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class AttachmentController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private FileUploadService fileUploadService;
    @Autowired
    private BaseAttachmentMapper baseAttachmentMapper;

    @GetMapping(value = "")
    public ModelAndView list() {
        ModelAndView view = new ModelAndView();
        view.setViewName("attachment/images");

        List<BaseAttachment> attachments = baseAttachmentMapper.selectByExample(new BaseAttachmentExample());
        for (BaseAttachment baseAttachment : attachments) {
            baseAttachment.setRemotePath(fileUploadService.getImgServer() + baseAttachment.getRemotePath());
        }
        view.addObject("attachments", attachments);
        return view;
    }

    @GetMapping(value = "upload")
    public ModelAndView upload() {
        ModelAndView view = new ModelAndView();
        view.setViewName("attachment/upload");

        return view;
    }
}
