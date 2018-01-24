package com.jamedow.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 用于API接口调用的工具类
 * Description
 * <p>
 * Created by Administrator on 2018/1/15.
 */
public class APIUtil {

    /**
     * 获取私钥key
     */
    private static final String PRIVATE_KEY = "00BE62C08DE8A6366D467D6555C268HH";

    /**
     * 功能描述: 接口参数排序加密算法<br>
     * 〈功能详细描述〉
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     * @author jiangzhou
     * @version [版本号, 2015年10月31日]
     * @since [产品/模块版本](可选)
     */
    public static String encrypt(String url, boolean isUrlDecoded) throws UnsupportedEncodingException {
        if (isUrlDecoded) {
            url = URLDecoder.decode(url, "UTF-8");
        }
        // url参数排序
        String sortUrl = APIUtil.sort(url);
        // 拼接秘钥
        sortUrl += "&key=" + PRIVATE_KEY;
        // MD5加密
        String encryptUrl = DigestUtils.md5Hex(sortUrl).toUpperCase();
        return encryptUrl;
    }

    // ASCII 升序排列
    public static String sort(String url) {
        // url参数键值对
        String resultUrl = "";
        List<String> strArray = new ArrayList<>();
        Map<String, String> mapRequest = APIUtil.URLRequest(url);
        for (String strRequestKey : mapRequest.keySet()) {
            //传送的sign参数不参与签名
            if (!strRequestKey.equals("sign")) {
                String strRequestValue = mapRequest.get(strRequestKey);
                url = strRequestKey + "=" + strRequestValue;
                strArray.add(url);
            }
        }
        Collections.sort(strArray);
        int count = 0;
        for (String param : strArray) {
            resultUrl += param;
            count++;
            if (count != strArray.size()) {
                resultUrl += "&";
            }
        }
        return resultUrl;
    }

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<>();
        String[] arrSplit = null;
        if (URL == null) {
            return mapRequest;
        }
        // 每个键值为一组
        arrSplit = URL.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            }
        }
        return mapRequest;
    }


    /**
     * @param args
     * @Test 功能描述: <br>
     * 参数获取排序测试类
     * @author jiangzhou
     * @version [版本号, 2015年10月31日]
     * @since [产品/模块版本](可选)
     */
    public static void main(String[] args) throws Exception {
        // 请求url
        String str = "city=nj&ad=123&sort=555&qq&banke=10&pid=1&welf=sss&sign=xxxxx888TTTTTT";
        String url = APIUtil.sort(str);
        String sign = "test";
        // 兼顾两种编码方式的hash值
        String encryptUrlDecoded = encrypt(url, true);
        String encryptUrl = encrypt(url, false);
        if (sign.equals(encryptUrl) || sign.equals(encryptUrlDecoded)) {
//            return true;
        } else {
            throw new Exception("非法来源请求");
        }
        System.out.println(url);
    }
}
