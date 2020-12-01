package com.tonglxer.grammer.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author Tong LinXing
 * @date 2020/12/1
 */
public class CGLBSendMessageMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects,
                            MethodProxy methodProxy) throws Throwable {
        System.out.println("我是CGLIB生成的代理函数，现在是原生方法调用前..");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("我是CGLIB生成的代理函数，现在是原生方法调用后..");
        return object;
    }
}
