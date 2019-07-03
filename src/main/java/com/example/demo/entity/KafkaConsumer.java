package com.example.demo.entity;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${kafkaConf.queueName}",groupId = "${kafkaConf.queueName}")
    public void topic_one(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){

        Optional message=Optional.ofNullable(record.value());
        if (message.isPresent()){
            Object msg=message.get();
            logger.info("被消费了： +++++++++++++++ Topic:" + topic+",Record:" + record+",Message:" + msg);
        }
    }

}