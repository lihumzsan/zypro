package com.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author LI.HU
 * @date 2019/1/21   17:28
 * <p>Description:
 * kafka消息接收
 * </p>
 */
@Component
public class KafkaReceiver {
    private static final Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);

    @KafkaListener(topics = {"${spring.kafka.consumer.topic}"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            logger.info("----------------- record =" + record);
            logger.info("------------------ message =" + message);
        }

    }
}
