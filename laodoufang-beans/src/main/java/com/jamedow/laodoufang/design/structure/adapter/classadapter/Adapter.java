package com.jamedow.laodoufang.design.structure.adapter.classadapter;

/**
 * 适配器模式（Adapter）
 * <p>
 * Description
 * 适配器模式将某个类的接口转换成客户端期望的另一个接口表示，目的是消除由于接口不匹配所造成的类的兼容性问题。
 * 主要分为三类：类的适配器模式、对象的适配器模式、接口的适配器模式。
 * <p>
 * 不改变原有接口，却还能使用新接口的功能。
 * <p>
 *
 * @author Administrator
 * @date 2018/3/7
 */
public class Adapter extends Adaptee implements Adapterable {

    public static void main(String[] args) {
        Adapterable adapterable = new Adapter();
        //用需要使用的方法，调取到了原始类的方法
        adapterable.Request();
    }

    @Override
    public void Request() {
        this.SpecificRequest();
    }
}