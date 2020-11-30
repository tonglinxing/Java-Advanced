package com.tonglxer.grammer.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author Tong LinXing
 * @date 2020/11/30
 */
public class SendMessageInvocationHandler implements InvocationHandler {

    private final Object target;

    public SendMessageInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是代理类，现在是调用目标类方法之前...");
        Object result = method.invoke(target, args);
        System.out.println("我是代理类，现在是调用目标类方法之后...");
        return result;
    }
}
