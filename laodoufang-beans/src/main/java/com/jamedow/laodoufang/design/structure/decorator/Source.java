package com.jamedow.laodoufang.design.structure.decorator;

/**
 * 被装饰类
 * <p>
 * Description
 * <p>
 *
 * @author Administrator
 * @date 2018/3/8
 */
public class Source implements Sourceable {
    public static void main(String[] args) {
        Sourceable source = new Source();
        Sourceable obj = new Decorator(source);
        obj.method();
    }

    @Override
    public void method() {
        System.out.println("the original method!");
    }
}
