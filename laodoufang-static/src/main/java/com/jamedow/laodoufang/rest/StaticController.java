package com.jamedow.laodoufang.rest;

import com.jamedow.laodoufang.entity.BaseAttachment;
import com.jamedow.laodoufang.service.FileUploadService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public Object upload(@RequestParam("file") List<MultipartFile> files, String site) {
        logger.info("site:[{}]", site);
        JSONArray results = new JSONArray();
        for (MultipartFile file : files) {
            JSONObject result = new JSONObject();
            BaseAttachment baseAttachment = fileUploadService.uploadFile(file);
            result.put("fileName", file.getOriginalFilename());
            result.put("remotePath", baseAttachment);
            results.add(result);
        }
        return results.toString();
    }

    @PostMapping(value = "delete", produces = "application/json;charset=utf-8")
    public Object delete(@RequestParam("resourceIds") String resourceIdsString) {
        if (StringUtils.isBlank(resourceIdsString)) {
            return "文件资源ID不能为空！";
        }
        String[] resourceIds = resourceIdsString.split(",");
        List<String> deleteObjects = fileUploadService.deleteFile(resourceIds);
        return JSONArray.fromObject(deleteObjects).toString();
    }
}
