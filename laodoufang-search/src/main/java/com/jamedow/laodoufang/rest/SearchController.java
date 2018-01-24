package com.jamedow.laodoufang.rest;

import com.jamedow.laodoufang.service.interfaces.SearchClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/16.
 */
@RestController
public class SearchController {
    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    private SearchClient searchClient;

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Object add(@RequestParam String content, @RequestParam String tags, @RequestParam String isOfficial,
                      @RequestParam Integer from, @RequestParam Integer pageSize) {
        return searchClient.search(content, tags, isOfficial, from, pageSize);
    }
}
