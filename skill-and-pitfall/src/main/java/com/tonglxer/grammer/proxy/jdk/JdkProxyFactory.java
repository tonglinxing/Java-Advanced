package com.tonglxer.grammer.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * 获取代理对象的工厂类
 *
 * @Author Tong LinXing
 * @date 2020/11/30
 */
public class JdkProxyFactory {

    /**
     * 动态生成代理类并返回
     *
     * @param target 目标类
     * @return 代理类
     * */
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
