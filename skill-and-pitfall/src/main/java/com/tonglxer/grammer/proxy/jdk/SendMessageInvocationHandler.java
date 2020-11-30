package com.tonglxer.grammer.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 *
 * @Author Tong LinXing
 * @date 2020/11/30
 */
public class SendMessageInvocationHandler implements InvocationHandler {

    // 目标类
    private final Object target;

    public SendMessageInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 当动态代理对象调用一个方法时候，
     * 这个方法的调用就会被转发到实现InvocationHandler 接口类的 invoke 方法来调用。
     *
     * @param proxy 动态生成的代理类
     * @param method 目标类调用的方法
     * @param args 目标类调用的方法的参数
     * */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是代理类，现在是调用目标类方法之前...");
        Object result = method.invoke(target, args);
        System.out.println("我是代理类，现在是调用目标类方法之后...");
        return result;
    }
}
