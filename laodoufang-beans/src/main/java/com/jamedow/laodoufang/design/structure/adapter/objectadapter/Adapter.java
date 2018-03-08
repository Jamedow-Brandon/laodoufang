package com.jamedow.laodoufang.design.structure.adapter.objectadapter;

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
public class Adapter implements Adapterable {

    // 直接关联被适配类
    private Adaptee adaptee;

    // 可以通过构造函数传入具体需要适配的被适配类对象
    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    public static void main(String[] args) {
        Adapterable adapterable = new Adapter(new Adaptee());
        //用需要使用的方法，调取到了原始类的方法
        adapterable.Request();
    }

    @Override
    public void Request() {
        this.adaptee.SpecificRequest();
    }
}