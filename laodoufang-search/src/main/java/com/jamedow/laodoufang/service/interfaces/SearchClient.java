package com.jamedow.laodoufang.service.interfaces;

import com.jamedow.laodoufang.service.impl.SearchClientHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Date: 2017/1/9</p>
 *
 * @author XN
 * @version 1.0
 */
@FeignClient(value = "laodoufang-search", fallback = SearchClientHystrix.class)
public interface SearchClient {

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    List<Map<String, Object>> search(String content, String tags, String isOfficial, int from, int pageSize);
}