package com.jamedow.laodoufang;

import com.jamedow.laodoufang.downloader.TestDownloader;
import com.jamedow.laodoufang.pipeline.TestPipeline;
import com.jamedow.laodoufang.processer.TestPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * Description
 * <p>
 * Created by Administrator on 2017/10/23.
 */
@Controller
@RequestMapping("test")
public class TestController {
    //webmagic主要由Downloader（下载器）、PageProcesser（解析器）、Schedule（调度器）和Pipeline（管道）四部分组成。

    @Autowired
    JedisPool jedisPool;
    @Autowired
    private TestPipeline testPipeline;

    @RequestMapping("crawler")
    public void testWorm() {
        Spider.create(new TestPageProcessor())
                .setScheduler(new RedisScheduler(jedisPool)
                        .setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                .addUrl("http://www.meishij.net")
                //从"https://github.com/code4craft"开始抓
                .addPipeline(testPipeline)
                .setDownloader(new TestDownloader())
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}
