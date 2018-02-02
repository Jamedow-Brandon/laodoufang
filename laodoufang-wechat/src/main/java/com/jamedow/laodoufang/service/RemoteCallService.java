package com.jamedow.laodoufang.service;

import com.jamedow.laodoufang.common.RestTemplateBean;
import com.jamedow.laodoufang.config.WeChatConstant;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Date: 2016/12/28</p>
 *
 * @author XN
 * @version 1.0
 */
@Service
public class RemoteCallService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取access token
     *
     * @param corpID 企业号id
     * @param secret 权限组密钥
     * @return
     */
    public String getAccessToken(
            String corpID, String secret
    ) {
        String accessToken = "";
        if (StringUtils.isBlank(corpID) || StringUtils.isBlank(secret)) {
            logger.error("getAccessToken parameters ERROR: corpID{},secret{}", corpID, secret);
            return "";
        }

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("corpid", corpID));
        params.add(new BasicNameValuePair("corpsecret", secret));

        logger.info("--get access token --");
        try {
            final String key = "accessToken:" + corpID + ":" + secret;
            if (redisTemplate.hasKey(key)) {
                logger.info("--no access token cache--");
                accessToken = String.valueOf(redisTemplate.opsForValue().get(key));
            } else {
                String result = RestTemplateBean.doGet(WeChatConstant.GET_ACCESS_TOKEN_URL, params);
                logger.info("--access token result-{}-", result);
                JSONObject obj = JSONObject.fromObject(result);
                if (obj.containsKey("access_token")) {
                    accessToken = String.valueOf(obj.get("access_token"));
                    // 有效时间 - 30 s 作为缓存时间
                    int expire = Integer.parseInt(String.valueOf(obj.get("expires_in")));
                    if (expire - 30 > 0) {
                        redisTemplate.opsForValue().set(key, accessToken, expire - 30, TimeUnit.SECONDS);
                    }
                } else if (obj.containsKey("errcode")) {
                    String errmsg = String.valueOf(obj.containsKey("errmsg") ? obj.get("errmsg") : "");
                    logger.error("getAccessToken call ERROR: {},{}", obj.containsKey("errcode"),
                            obj.containsKey(errmsg));
                }
            }

        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }

        return accessToken;
    }

    public JSONObject getUserInfo(
            String code, String accessToken
    ) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(accessToken)) {
            logger.error("getAccessToken parameters ERROR: code{},accessToken{}", code, accessToken);
            return null;
        }

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("access_token", accessToken));

        try {
            final String url = WeChatConstant.getUserInfo(accessToken, code);
            final String result = RestTemplateBean.doGet(url, params);
            logger.info("====getUserInfo===={}", result);
            JSONObject obj = JSONObject.fromObject(result);
            return obj;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据userid获取openid
    public JSONObject getOpenId(
            String userId, String agentId, String accessToken
    ) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(accessToken)) {
            logger.error("getOpenId parameters ERROR: userId{},agentId{},accessToken{}", userId, agentId, accessToken);
            return null;
        }

        JSONObject params = new JSONObject();
        params.put("userid", userId);
        params.put("agentid", agentId);
        params.put("access_token", accessToken);

        final String url = WeChatConstant.getOpenIdURL(accessToken);
        final String result = RestTemplateBean.doPost(url, params);
        logger.info("====getOpenId===={}", result);
        JSONObject obj = JSONObject.fromObject(result);
        return obj;
    }

    /**
     * 获取access token
     *
     * @param corpID 企业号id
     * @param secret 权限组密钥
     * @return
     */
    public String getJsapiTicket(
            String corpID, String secret
    ) {
        String ticket = "";


        logger.info("--get jsapi ticket --");
        try {
            final String key = "jsapi_ticket:" + corpID + ":" + secret;
            if (redisTemplate.hasKey(key)) {
                logger.info("--get jsapi ticket cache--");
                ticket = String.valueOf(redisTemplate.opsForValue().get(key));
            } else {
                JSONObject params = new JSONObject();

                String accessToken = this.getAccessToken(corpID, secret);
                String url = WeChatConstant.getJsapiTicket(accessToken);
                String result = RestTemplateBean.doPost(url, params);
                logger.info("====getJsapiTicket===={}", result);
                JSONObject obj = JSONObject.fromObject(result);
                if (obj.containsKey("ticket")) {
                    ticket = String.valueOf(obj.get("ticket"));
                    // 有效时间 - 30 s 作为缓存时间
                    int expire = Integer.parseInt(String.valueOf(obj.get("expires_in")));
                    if (expire - 30 > 0) {
                        redisTemplate.opsForValue().set(key, ticket, expire - 30, TimeUnit.SECONDS);
                    }
                } else if (obj.containsKey("errcode")) {
                    String errmsg = String.valueOf(obj.containsKey("errmsg") ? obj.get("errmsg") : "");
                    logger.error("getJsapiTicket call ERROR: {},{}", obj.containsKey("errcode"),
                            obj.containsKey(errmsg));
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }


        return ticket;
    }
}
