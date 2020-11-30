package com.tonglxer.grammer.proxy;

import com.tonglxer.grammer.proxy.jdk.JdkProxyFactory;
import com.tonglxer.grammer.proxy.jdk.SendMessage;
import com.tonglxer.grammer.proxy.jdk.SendMessageImpl;

/**
 * @Author Tong LinXing
 * @date 2020/11/30
 */
public class ProxyDemo {
    public static void main(String[] args) {
        Object object = JdkProxyFactory.getProxy(new SendMessageImpl());
        if (object instanceof SendMessage) {
            SendMessage sendMessage = (SendMessage)object;
            sendMessage.send("Hello Proxy.");
        }
    }
}
