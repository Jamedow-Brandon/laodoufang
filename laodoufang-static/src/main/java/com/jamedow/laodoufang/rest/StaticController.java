package com.jamedow.laodoufang.rest;

import com.jamedow.laodoufang.service.FileUploadService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/25.
 */
@RestController
@RequestMapping("/static")
public class StaticController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping(value = "upload", produces = "application/json;charset=utf-8")
    public Object upload(MultipartHttpServletRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        JSONArray results = new JSONArray();
        for (MultipartFile file : files) {
            JSONObject result = new JSONObject();
            String remotePath = fileUploadService.uploadFile(file);
            result.put("fileName", file.getOriginalFilename());
            result.put("remotePath", remotePath);
            results.put(result);
        }
        return results.toString();
    }
}
