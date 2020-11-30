package com.tonglxer.grammer.proxy.jdk;

/**
 * @Author Tong LinXing
 * @date 2020/11/30
 */
public class SendMessageImpl implements SendMessage{
    @Override
    public String send(String message) {
        System.out.println("目标类发送的内容：" + message);
        return message;
    }
}
