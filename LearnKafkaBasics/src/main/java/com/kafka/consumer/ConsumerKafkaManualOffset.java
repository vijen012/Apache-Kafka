package com.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

public class ConsumerKafkaManualOffset {
    public static void main(String[] args) {
        Properties properties=new Properties();
        properties.put("bootstrap.servers", "localhost:9092,localhost:9093");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("enable.auto.commit", false);
        properties.put("group.id","test1");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        ArrayList<String> topics = new ArrayList<String>();
        topics.add("demo-topic");
        kafkaConsumer.subscribe(topics);
        try {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
                consumerRecords.forEach(consumerRecord -> {
                    System.out.println("Record read from demo-topic "+ consumerRecord.toString());
                    //it should get executed once the processing is complete of record
                    kafkaConsumer.commitSync();
                });
            }

        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        finally {
            kafkaConsumer.close();
        }

    }
}
