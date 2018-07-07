package com.jamedow.laodoufang.web;

import com.jamedow.laodoufang.common.SHA1;
import com.jamedow.laodoufang.common.WXBizMsgCrypt;
import com.jamedow.laodoufang.config.WeChatProperties;
import com.jamedow.laodoufang.service.RemoteCallService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/2/2.
 */
@RestController
public class WeChatController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private RemoteCallService remoteCallService;

    @RequestMapping(value = "checkWeChatCallback", method = RequestMethod.GET)
    public String checkWeChatCallback(@RequestParam(value = "signature") String sVerifyMsgSig,
                                      @RequestParam(value = "timestamp") String sVerifyTimeStamp,
                                      @RequestParam(value = "nonce") String sVerifyNonce,
                                      @RequestParam(value = "echostr") String sVerifyEchoStr) throws Exception {
        logger.info("signature:[{}]timestamp:[{}]nonce[{}]echostr：[{}]", sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
        WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(weChatProperties.getToken(),
                weChatProperties.getEncodingAESKey(),
                weChatProperties.getAppId());

        String sEchoStr = "hahahahahahaha"; //需要返回的明文
        try {
            sEchoStr = wxBizMsgCrypt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
            System.out.println("verifyUrl echostr: " + sEchoStr);
            // 验证URL成功，将sEchoStr返回
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            logger.error(e.getMessage(), e);
        }
        return sEchoStr;
    }

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
            String jsapiTicket = remoteCallService.getJsapiTicket(weChatProperties.getAppId(),
                    weChatProperties.getSecret());
            String nonceStr = "Wm3WZYTPz0wzccnW";
            String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

            StringBuffer sb = new StringBuffer();

            sb.append("jsapi_ticket=" + jsapiTicket);
            sb.append("&noncestr=" + nonceStr);
            sb.append("&timestamp=" + timestamp);
            sb.append("&url=" + requestUrl);

            String str = sb.toString();
            //获取分享 jsapi签名
            String signature = SHA1.getSHA1(str);

            result.put("appId", weChatProperties.getAppId());
            result.put("nonceStr", nonceStr);
            result.put("timestamp", timestamp);
            result.put("signature", signature);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result.toString();
    }

}
