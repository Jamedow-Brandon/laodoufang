package com.jamedow.laodoufang.design.structure.adapter.classadapter;

/**
 * 类适配器适配接口
 * <p>
 * Description
 * 实现此接口，可以对类进行适配
 * <p>
 * Created by Administrator on 2018/3/7.
 */
public interface Adapterable {
    /* 与原类中的方法相同 */
    public void SpecificRequest();

    /* 需要使用的方法 */
    public void Request();
}
