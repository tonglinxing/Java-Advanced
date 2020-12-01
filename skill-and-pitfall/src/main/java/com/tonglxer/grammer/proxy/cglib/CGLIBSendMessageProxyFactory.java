package com.tonglxer.grammer.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;

/**
 * 通过 Enhancer类来动态获取被代理类.
 * 当代理类调用方法的时候,
 * 实际调用的是 MethodInterceptor 中的 intercept 方法.
 *
 * @Author Tong LinXing
 * @date 2020/12/1
 */
public class CGLIBSendMessageProxyFactory {

    /**
     * cglib动态生成代理对象
     *
     * @param clazz 被代理的对象的类
     * @return 动态生成的代理类
     * */
    public static Object getProxy(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        // 被代理的对象所属的类的类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 被代理对象所属的类
        enhancer.setSuperclass(clazz);
        // 自定义的MethodInterceptor类
        enhancer.setCallback(new CGLBSendMessageMethodInterceptor());
        return enhancer.create();
    }
}
