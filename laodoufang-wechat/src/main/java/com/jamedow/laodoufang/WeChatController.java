package com.jamedow.laodoufang;

import com.jamedow.laodoufang.config.WeChatProperties;
import com.jamedow.laodoufang.service.RemoteCallService;
import com.jamedow.utils.AesException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/2/2.
 */
@RestController
@RequestMapping("wechat")
public class WeChatController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private RemoteCallService remoteCallService;

    @RequestMapping(value = "/config-wx-js", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getShareUrl(String requestUrl) {
//        String requestUrl = request.getScheme() //当前链接使用的协议
//                + "://" + request.getServerName()//服务器地址
//                + request.getContextPath() //应用名称，如果应用名称为
//                + request.getServletPath() //请求的相对url
//                + "?" + request.getQueryString(); //请求参数

        JSONObject result = new JSONObject();
        try {
            //获取jsapi ticket
            String jsapiTicket = remoteCallService.getJsapiTicket(weChatProperties.getCorpId(),
                    weChatProperties.getSecret());
            String nonceStr = "Wm3WZYTPz0wzccnW";
            String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
            //获取分享 jsapi签名
            String signature = this.getSHA1(jsapiTicket, nonceStr, String.valueOf(timestamp), requestUrl);

            result.put("appId", weChatProperties.getCorpId());
            result.put("nonceStr", nonceStr);
            result.put("timestamp", timestamp);
            result.put("signature", signature);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result.toString();
    }

    /**
     * 用SHA1算法生成安全签名
     *
     * @param jsapiTicket 票据
     * @param nonceStr    随机字符串
     * @param timestamp   时间戳
     * @param url         密文
     * @return 安全签名
     * @throws AesException
     */
    public String getSHA1(String jsapiTicket, String nonceStr, String timestamp, String url) throws AesException {
        try {
            StringBuffer sb = new StringBuffer();

            sb.append("jsapi_ticket=" + jsapiTicket);
            sb.append("&noncestr=" + nonceStr);
            sb.append("&timestamp=" + timestamp);
            sb.append("&url=" + url);

            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.ComputeSignatureError);
        }
    }
}
