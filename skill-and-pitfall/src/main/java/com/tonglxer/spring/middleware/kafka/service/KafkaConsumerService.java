package com.tonglxer.spring.middleware.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * 消息消费者
 *
 * @Author Tong LinXing
 * @date 2021/1/31
 */
@Slf4j
public class KafkaConsumerService {

    /**
     * 监听主题"TONG"下的消息
     * @param data
     */
    @KafkaListener(topics = {SendKafkaMessageService.topic})
    public void listenFromMovieTopic(String data){
        log.info("Consumer message: {}", data);
    }
}
