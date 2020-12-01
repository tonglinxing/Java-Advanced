package com.tonglxer.grammer.proxy.cglib;

/**
 * cglib动态代理机制不需要基于接口
 *
 * @Author Tong LinXing
 * @date 2020/12/1
 */
public class CGLIBSendMessage {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
