package com.tonglxer.grammer.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;

/**
 * @Author Tong LinXing
 * @date 2020/12/1
 */
public class CGLIBSendMessageProxyFactory {
    public static Object getProxy(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CGLBSendMessageMethodInterceptor());
        return enhancer.create();
    }
}
