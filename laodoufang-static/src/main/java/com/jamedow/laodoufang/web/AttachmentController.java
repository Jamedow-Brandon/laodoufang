package com.jamedow.laodoufang.web;

import com.jamedow.laodoufang.entity.BaseAttachment;
import com.jamedow.laodoufang.entity.BaseAttachmentExample;
import com.jamedow.laodoufang.mapper.BaseAttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/25.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/attachment")
public class AttachmentController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BaseAttachmentMapper baseAttachmentMapper;

    @GetMapping(value = "list")
    public ModelAndView list() {
        ModelAndView view = new ModelAndView();
        view.setViewName("attachment/list");

        List<BaseAttachment> attachments = baseAttachmentMapper.selectByExample(new BaseAttachmentExample());
        view.addObject("attachments", attachments);

        return view;
    }
}
