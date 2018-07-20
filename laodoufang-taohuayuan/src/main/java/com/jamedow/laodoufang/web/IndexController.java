package com.jamedow.laodoufang.web;

import com.jamedow.laodoufang.mongo.bean.FormData;
import com.jamedow.laodoufang.mongo.service.TestMongoService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/26.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class IndexController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestMongoService testMongoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String play() {
//        Map<String, Object> data = new HashMap<>();
//        data.put("t", 20);
//        data.put("s", 30);
//        data.put("a", 44);
//
//        FormData test1 = new FormData();
//        test1.setId(1L);
//        test1.setData(data);
//        testMongoService.insert(test1);

        List<FormData> formDataList = testMongoService.findAll();

        logger.info("formDataList are :[{}]", JSONArray.fromObject(formDataList).toString());
        return "index";
    }

}
