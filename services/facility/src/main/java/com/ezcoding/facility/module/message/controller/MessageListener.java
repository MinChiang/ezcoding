package com.ezcoding.facility.module.message.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-04-16 15:40
 */
@Component
public class MessageListener {

    //    @KafkaListener(topics = "example")
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.println("key值为：" + record.key());
        System.out.println("value值为：" + record.value());
    }

}
