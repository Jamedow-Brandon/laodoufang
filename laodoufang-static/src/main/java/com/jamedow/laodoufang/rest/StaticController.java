package com.jamedow.laodoufang.rest;

import com.jamedow.laodoufang.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/25.
 */
@Controller
@RequestMapping("/static")
public class StaticController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("upload")
    public String upload(MultipartHttpServletRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        for (MultipartFile file : files) {
            fileUploadService.uploadFile(file);
        }
        return null;
    }
}
