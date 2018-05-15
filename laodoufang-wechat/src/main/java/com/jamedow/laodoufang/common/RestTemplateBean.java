package com.jamedow.laodoufang.common;

import com.jamedow.laodoufang.utils.UrlSortUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description
 * <p>
 * Created by 365 on 2017/1/3 0003.
 */
public class RestTemplateBean {
    /**
     * 获取私钥key
     */
    private static final String PRIVATE_KEY = "00BE62C08DE8A6366D467D6555C268HH";

    private final RestTemplate restTemplate;

    public RestTemplateBean(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public static String doPost(String method, JSONObject requestBody) {
        method = encrypt(method, true);
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplateBean restTemplateBean = new RestTemplateBean(restTemplateBuilder);
        return restTemplateBean.postJsonBody(method, requestBody);
    }

    public static String doGet(String path, List<NameValuePair> params) throws URISyntaxException {
        return doGet(getUri(path, params));
    }

    public static String doGet(String path) throws URISyntaxException {
        path = encrypt(path, true);
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplateBean restTemplateBean = new RestTemplateBean(restTemplateBuilder);

        System.out.println("call url :" + path);
        return restTemplateBean.get(path);
    }

    public static String getUri(String path, List<NameValuePair> params) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(path);
        builder.setParameters(params);
        return String.valueOf(builder.build());
    }

    /**
     * 功能描述: 接口参数排序加密算法<br>
     * 〈功能详细描述〉
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String url, boolean isUrlDecoded) {
        if (isUrlDecoded) {
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // url参数排序
        String sortUrl = UrlSortUtils.sort(url.substring(url.lastIndexOf("?") + 1));
        // 拼接秘钥
        sortUrl += "&key=" + PRIVATE_KEY;
        // MD5加密
        String encryptUrl = DigestUtils.md5Hex(sortUrl).toUpperCase();
        return url + "&sign=" + encryptUrl;
    }

    private String get(String path) {
        return restTemplate.getForObject(path, String.class);
    }

    private String postJsonBody(String method, JSONObject requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        JSONObject postBody = new JSONObject();

        for (Iterator<Map.Entry> iter = requestBody.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry item = iter.next();
            if (!(item.getValue() instanceof String)) {
                postBody.put(item.getKey(), item.getValue());
            } else if (item.getValue() != null && ((item.getValue() instanceof String) && StringUtils.isNotBlank(
                    String.valueOf(item.getValue())))) {
                postBody.put(item.getKey(), item.getValue());
            }
        }

        HttpEntity<JSONObject> entity = new HttpEntity<>(postBody, headers);
        System.out.println("call url :" + method + ",postBody" + postBody);
        return restTemplate.postForObject(method, entity, String.class);
    }
}
