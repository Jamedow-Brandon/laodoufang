package com.jamedow.laodoufang.rest;

import com.jamedow.laodoufang.service.SearchService;
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
    private SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Object search(@RequestParam String index, @RequestParam String type,
                         @RequestParam String content, @RequestParam String tags, @RequestParam String isOfficial,
                         @RequestParam Integer from, @RequestParam Integer pageSize) {
        return searchService.search(index, type, content, tags, isOfficial, from, pageSize);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Object create(@RequestParam String index, @RequestParam String type,
                         @RequestParam String source) {
        return searchService.insertRecipeToEs(index, type, source);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Object delete(@RequestParam String index, @RequestParam String type,
                         @RequestParam String searchDocumentId) {
        return searchService.delete(index, type, searchDocumentId);
    }
}
