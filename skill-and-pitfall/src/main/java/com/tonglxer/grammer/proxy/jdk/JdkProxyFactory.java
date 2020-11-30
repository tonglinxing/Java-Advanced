package com.tonglxer.grammer.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * @Author Tong LinXing
 * @date 2020/11/30
 */
public class JdkProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                // 目标类的类加载器
                target.getClass().getClassLoader(),
                // 目标类的接口
                target.getClass().getInterfaces(),
                // 自定义目标对象的代理类
                new SendMessageInvocationHandler(target)
        );
    }
}
