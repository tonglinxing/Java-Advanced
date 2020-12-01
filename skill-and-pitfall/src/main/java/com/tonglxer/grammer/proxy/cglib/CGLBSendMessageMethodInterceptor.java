package com.tonglxer.grammer.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author Tong LinXing
 * @date 2020/12/1
 */
public class CGLBSendMessageMethodInterceptor implements MethodInterceptor {

    /**
     * 自定义方法拦截并进行增强实现
     *
     * @param o 被代理的对象
     * @param method 被拦截的方法
     * @param args 方法入参
     * @param methodProxy 用于调用原始方法
     * @return 被拦截的方法的返回值
     * */
    @Override
    public Object intercept(Object o, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        System.out.println("我是CGLIB生成的代理函数，现在是原生方法调用前..");
        Object object = methodProxy.invokeSuper(o, args);
        System.out.println("我是CGLIB生成的代理函数，现在是原生方法调用后..");
        return object;
    }
}
