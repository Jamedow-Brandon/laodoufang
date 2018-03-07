package com.jamedow.laodoufang.design.structure.adapter.classadapter;

/**
 * 类的适配器模式
 * <p>
 * Description
 * 有一个Source类，拥有一个方法，待适配，目标接口时Targetable，通过Adapter类，将Source的功能扩展到Targetable里
 * <p>
 *
 * @author Administrator
 * @date 2018/3/7
 */
public class Adaptee {
    public void SpecificRequest() {
        System.out.println("this is original method!");
    }
}
