package com.jamedow.laodoufang.utils;

import java.util.*;

/**
 * URL请求参数获取及排序功能
 *
 * @author jamedow
 * @version [版本号, 2018年03月01日]
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class UrlSortUtils {

    /**
     * 解析出url请求的路径，包括页面
     *
     * @param strURL url地址
     * @return url路径
     */
    public static String UrlPage(String strURL) {
        String strPage = null;
        String[] arrSplit = null;
        strURL = strURL.trim().toLowerCase();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 0) {
            if (arrSplit.length > 1) {
                if (arrSplit[0] != null) {
                    strPage = arrSplit[0];
                }
            }
        }
        return strPage;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    /*
     * private static String TruncateUrlPage(String strURL) { String strAllParam = null; String[] arrSplit = null;
     * strURL = strURL.trim().toLowerCase(); arrSplit = strURL.split("[?]"); if (strURL.length() > 1) { if
     * (arrSplit.length > 1) { if (arrSplit[1] != null) { strAllParam = arrSplit[1]; } } } return strAllParam; }
     */

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        // String strUrlParam = TruncateUrlPage(URL);
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

    // ASCII 升序排列
    public static String sort(String url) {
        // url参数键值对
        String resultUrl = "";
        List<String> strArray = new ArrayList<>();
        Map<String, String> mapRequest = UrlSortUtils.URLRequest(url);
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
     * @param args
     * @Test 功能描述: <br>
     * 参数获取排序测试类
     * @author jiangzhou
     * @version [版本号, 2015年10月31日]
     * @since [产品/模块版本](可选)
     */
    public static void main(String[] args) {
        // 请求url
        String str = "city=nj&ad=123&sort=555&qq&banke=10&pid=1&welf=sss&sign=xxxxx888TTTTTT";
        String url = UrlSortUtils.sort(str);
        System.out.println(url);
    }

}
