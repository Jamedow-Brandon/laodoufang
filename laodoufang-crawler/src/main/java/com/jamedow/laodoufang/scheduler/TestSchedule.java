package com.jamedow.laodoufang.scheduler;

import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * Description
 * <p>
 * Created by Administrator on 2017/10/25.
 */
public class TestSchedule extends RedisScheduler {
    public TestSchedule(JedisPool pool) {
        super(pool);
    }
}
