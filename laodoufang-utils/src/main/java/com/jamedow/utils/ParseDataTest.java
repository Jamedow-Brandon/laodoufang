package com.jamedow.utils;

import net.sf.json.JSONObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/2/23.
 */
public class ParseDataTest {

    /**
     * 成功数量
     */
    public static AtomicInteger successCount = new AtomicInteger();

    private static Set<Object> tempFailDataList = new HashSet<>();

    /**
     * 通过源list数据的逐条转移实现
     *
     * @param sourList   源list
     * @param batchCount 分组条数
     */
    public static void dealByRemove(List<Object> sourList, int batchCount) {

        List<Object> tempList = new ArrayList<>();
        for (int i = 0; i < sourList.size(); i++) {
            tempList.add(sourList.get(i));
            if ((i + 1) % batchCount == 0 || (i + 1) == sourList.size()) {
                final List<Object> currentTempList = new ArrayList<>(tempList);
                Thread t = new ParseDataThread(currentTempList, successCount);
                t.start();
                tempList.clear();
            }
        }
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

        System.out.println("success count " + successCount);

        //处理失败的数据需要重新处理；成功的不需要再次处理；
        Set<Object> currentFailDataList = new HashSet<>(tempFailDataList);
        while (currentFailDataList.size() > 0) {
            for (Iterator<Object> it = currentFailDataList.iterator(); it.hasNext(); ) {
                if (parseData(it.next())) {
                    it.remove();
                    successCount.getAndIncrement();
                }
            }
        }
    }

    /**
     * 处理数据
     *
     * @param object
     * @return true 成功；false 失败；
     */
    private static boolean parseData(Object object) {
        int random = (int) (1 + Math.random() * (10 - 1 + 1));
        if (random < 10) {
            String objectStr = JSONObject.fromObject(object).toString();
//            System.out.println("parse object result:" + objectStr);
            return true;
        } else {
            //置入失败队列
            tempFailDataList.add(object);
            return false;
        }
    }

    /**
     * 测试主方法
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        long start = System.nanoTime();

        //分组条数
        int dataBatchCount = 10;
        //每次处理组数
        int dataParseBatchCount = 5;
        dealByRemove(list, dataBatchCount * dataParseBatchCount);

        long end = System.nanoTime();
        System.out.println("The elapsed time :" + (end - start));
        System.out.println("finally parse success count " + successCount);

    }

    public static class ParseDataThread extends Thread {
        private List<Object> sourList;
        private AtomicInteger successCount;

        ParseDataThread(List<Object> sourList, AtomicInteger successCount) {
            this.sourList = sourList;
            this.successCount = successCount;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "开始...");
            int singleSuccessCount = 0;
            int singleFailCount = 0;
            for (Object o : sourList) {
                if (parseData(o)) {
                    successCount.getAndIncrement();
                    singleSuccessCount++;
                } else {
                    singleFailCount++;
                }
            }

            System.out.println("single success count " + singleSuccessCount);
            System.out.println("single fail count " + singleFailCount);
        }
    }
}
