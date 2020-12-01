package com.tonglxer.grammer.proxy.cglib;

/**
 * @Author Tong LinXing
 * @date 2020/12/1
 */
public class CGLIBSendMessage {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
