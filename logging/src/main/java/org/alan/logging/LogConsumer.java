package org.alan.logging;

import org.longjiang.core.log.LogMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/6/12.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class LogConsumer {

    @Autowired
    private MongoTemplate mongoTemplate;

//    @KafkaListener(id = "consumer1", topicPartitions = @TopicPartition(topic = "longjiang-log",
//            partitionOffsets =
//            @PartitionOffset(partition = "0", initialOffset = "0")))
//    public void processMessage(@Header(KafkaHeaders.OFFSET) int offset, LogMessage message) {
//        System.out.println("[" + offset + "]" + message);
//    }

    @KafkaListener(topics = "longjiang-log")
    public void processMessage2(
            LogMessage message, @Header(KafkaHeaders.OFFSET) long offset, Acknowledgment ack) {
        String topic = "longjiang-log";
        message.offset = offset;
        System.out.println("[" + offset + "]" + message);
        mongoTemplate.insert(message, topic);
        ack.acknowledge();
    }
}
