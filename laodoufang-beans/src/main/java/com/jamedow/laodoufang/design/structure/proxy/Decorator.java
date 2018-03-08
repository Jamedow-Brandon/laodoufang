package com.jamedow.laodoufang.design.structure.proxy;

/**
 * 装饰类
 * <p>
 * 装饰模式的本质是动态组合
 * 装饰模式与代理模式的区别：可以多次装饰，但只能代理一次
 * <p>
 * Description
 * 装饰器模式的应用场景：
 * 1、需要扩展一个类的功能。
 * 2、动态的为一个对象增加功能，而且还能动态撤销。（继承不能做到这一点，继承的功能是静态的，不能动态增删。）
 * 缺点：产生过多相似的对象，不易排错！
 * <p>
 *
 * @author Administrator
 * @date 2018/3/8
 */
public class Decorator implements Sourceable {
    private Sourceable source;

    public Decorator() {
        Source source = new Source();
        this.source = source;
    }

    @Override
    public void method() {
        System.out.println("before proxy!");
        source.method();
        System.out.println("after proxy!");
    }
}
