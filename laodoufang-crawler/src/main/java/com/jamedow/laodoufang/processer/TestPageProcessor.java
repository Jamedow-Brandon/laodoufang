package com.jamedow.laodoufang.processer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Description
 * <p>
 * Created by Administrator on 2017/10/20.
 */
@Component
public class TestPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public Site getSite() {
//        site.addCookie("", "")
//                .setCharset("")
//                .setUserAgent("")
//                .setTimeOut(0)
//                .setRetryTimes(1)
//                .setCycleRetryTimes(0)
//                .setDomain("")
//                .addHeader("", "");
        return site;
    }

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        if (page.getHtml().xpath("//*[@id=\"tongji_title\"]") == null) {
            page.setSkip(true);
        }
//            page.setSkip(true);
        String name = page.getHtml().xpath("//*[@id=\"tongji_title\"]").css("a", "text").toString();
        String centerImg = page.getHtml().xpath("//*[@class=\"cp_headerimg_w\"]").css("img", "src").toString();
        String cookery = page.getHtml().xpath("//*[@id=\"tongji_gy\"]").css("a", "text").toString();
        String taste = page.getHtml().xpath("//*[@id=\"tongji_kw\"]").css("a", "text").toString();
        String intro = page.getHtml().xpath("//*[@class=\"materials\"]").css("p", "text").toString();
        JSONArray ingredients = new JSONArray();
        List<Selectable> ingredientSelects = page.getHtml().xpath("//*[@class=\"zl\"]/ul/li/div/h4").nodes();
        for (Selectable ingredientSelect : ingredientSelects) {
            String ingredientName = ingredientSelect.css("a", "text").toString();
            String ingredientValue = ingredientSelect.css("span", "text").toString();
            JSONObject ingredient = new JSONObject();
            ingredient.put("name", ingredientName);
            ingredient.put("weight", ingredientValue);
            ingredients.add(ingredient);
        }

        JSONArray burdenings = new JSONArray();
        List<Selectable> burdeningSelects = page.getHtml().xpath("//*[@class=\"fuliao\"]/ul/li/div/h4").nodes();
        for (Selectable burdeningSelect : burdeningSelects) {
            String burdeningName = burdeningSelect.css("a", "text").toString();
            String burdeningValue = burdeningSelect.css("span", "text").toString();
            JSONObject burdening = new JSONObject();
            burdening.put("name", burdeningName);
            burdening.put("weight", burdeningValue);
            burdenings.add(burdening);
        }

        String detail = page.getHtml().xpath("//*[@class=\"measure\"]").css(".editnew").toString();

        page.putField("name", name);
        page.putField("intro", intro);
        page.putField("detail", detail);
        page.putField("centerImg", centerImg);
        page.putField("ingredient", ingredients.toJSONString());
        page.putField("burdening", burdenings.toJSONString());


        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://www.meishij.net/zuofa/[\\w\\-]+.html)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://www.meishij.net/[\\w\\-]+/[\\w\\-]+/[\\w\\-]+/)").all());
    }
}
