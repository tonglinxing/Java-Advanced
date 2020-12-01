package com.tonglxer.grammer.proxy;

import com.tonglxer.grammer.proxy.cglib.CGLIBSendMessage;
import com.tonglxer.grammer.proxy.cglib.CGLIBSendMessageProxyFactory;
import com.tonglxer.grammer.proxy.jdk.JdkProxyFactory;
import com.tonglxer.grammer.proxy.jdk.SendMessage;
import com.tonglxer.grammer.proxy.jdk.SendMessageImpl;

/**
 * @Author Tong LinXing
 * @date 2020/11/30
 */
public class ProxyDemo {
    public static void main(String[] args) {
        jdkProxyDemo();
        System.out.println();
        cglibProxyDemo();
    }

    /**
     * 1. 定义一个接口及其实现类.
     * 2. 自定义 InvocationHandler 并重写invoke方法,
     *    在 invoke 方法中调用原生方法（被代理类的方法）并自定义一些处理逻辑.
     * 3. 通过 Proxy.newProxyInstance方法创建代理对象.
     *
     * */
    private static void jdkProxyDemo() {
        Object object = JdkProxyFactory.getProxy(new SendMessageImpl());
        if (object instanceof SendMessage) {
            SendMessage sendMessage = (SendMessage)object;
            sendMessage.send("Hello jdk Proxy.");
        }
    }

    private static void cglibProxyDemo() {
        Object object = CGLIBSendMessageProxyFactory.getProxy(CGLIBSendMessage.class);
        if (object instanceof CGLIBSendMessage) {
            CGLIBSendMessage sendMessage = (CGLIBSendMessage)object;
            sendMessage.send("Hello cglib Proxy.");
        }
    }
}
