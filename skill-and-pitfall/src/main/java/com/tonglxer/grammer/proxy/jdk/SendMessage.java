package com.tonglxer.grammer.proxy.jdk;

/**
 * JDK方式实现的动态代理
 * 只能代理实现了接口的类
 *
 * @Author Tong LinXing
 * @date 2020/11/30
 */
public interface SendMessage {
    String send(String message);
}
