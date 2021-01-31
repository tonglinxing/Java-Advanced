package com.tonglxer.spring.middleware.kafka.controller;

import com.tonglxer.spring.middleware.kafka.service.SendKafkaMessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息生产者
 *
 * @Author Tong LinXing
 * @date 2021/1/31
 */
@RestController
public class SendMessageController {
    // 消息发送服务类
    private final SendKafkaMessageService sendKafkaMessageService;

    @Autowired
    public SendMessageController(SendKafkaMessageService sendKafkaMessageService) {
        this.sendKafkaMessageService = sendKafkaMessageService;
    }

    /**
     * 向kafka发送消息
     *
     * @param message 发送的消息
     * @return 发送的消息
     */
    @RequestMapping(method = RequestMethod.GET, value = "/send/{message}")
    public String sendToMovie(@PathVariable String message){
        sendKafkaMessageService.sendToKafka(message);
        return message;
    }

}
