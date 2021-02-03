package com.tonglxer.spring.middleware.kafka.service;

import com.tonglxer.spring.middleware.kafka.config.KafkaProducerConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * 发送消息
 *
 * @Author Tong LinXing
 * @date 2021/1/31
 */
@Service
@Slf4j
public class SendKafkaMessageService {
    /**
     * 定义一个主题
     */
    public static final String topic = "TONG";

    /**
     * kafka消息模板
     * Spring Boot根据application.yml属性文件中
     * 配置的属性自动配置并初始化KafkaTemplate。
     */
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Autowired
    public SendKafkaMessageService(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 发送消息到kafka的对应topic中
     *
     * @param message 消息
     */
    public void sendToKafka(String message){
        log.info("start send message...");
        kafkaTemplate.send(topic, message);
        log.info("The message has been sent.");
    }
}
