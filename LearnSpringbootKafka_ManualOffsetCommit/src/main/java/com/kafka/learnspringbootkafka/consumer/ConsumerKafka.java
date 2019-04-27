package com.kafka.learnspringbootkafka.consumer;

import com.kafka.learnspringbootkafka.data.Person;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class ConsumerKafka implements AcknowledgingMessageListener<String, String> {

    @Override
    @KafkaListener(id = "consumer-1", topics = {"${kafka.topic}"}, containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> data,
            Acknowledgment acknowledgment) {
        // TODO Auto-generated method stub
        try {
            System.out.println("Read Record is : " + data.value());
            System.out.println("Offset is : " + data.offset());
            System.out.println("Topic is : " + data.topic());
            System.out.println("Partition is : " + data.partition());

        } catch (Exception e ){
            System.out.println("Push the messaged to Error Stream : " + e);
        } finally{
            acknowledgment.acknowledge();
        }
    }

    @KafkaListener(id = "consumer-2", topics = {"${kafka.person.topic}"}, containerFactory = "personKafkaListenerContainerFactory" )
    public void listenPersonData(
            @Payload Person person,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.TOPIC) String topic) {
        System.out.println("Received Message: " + person + " from partition: " + partition);
    }

/*
    @KafkaListener(id = "consumer-2", topics = {"${kafka.topic}"} )
    public void listen(String message) {
        System.out.println("Received Messasge " + message);
    }

    @KafkaListener(id = "consumer-3", topics = {"${kafka.topic}"} )
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println("Received Message: " + message + " from partition: " + partition);
    }

  */


//Consuming Messages from a Specific Partition
/*    @KafkaListener(topicPartitions = @TopicPartition(topic = "topicName",
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0"),
                            @PartitionOffset(partition = "3", initialOffset = "0")
                    }))
    public void listenToParition(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println(
                "Received Messasge: " + message
                        + "from partition: " + partition);
    }*/

}
