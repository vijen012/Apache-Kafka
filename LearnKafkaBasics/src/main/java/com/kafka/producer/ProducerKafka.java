package com.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerKafka {
    public static void main(String[] args) {
        Properties properties=new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //Custom Partition
//        properties.put("partitioner.class", "com.kafka.partitioner.KafkaProducerPartitioner");


        KafkaProducer<String,String> myProducer= new KafkaProducer<String, String>(properties);

        try {
            for(int i=50; i<200; i++) {
                //Direct Partitioning
                //myProducer.send(new ProducerRecord<String, String>("demo-topic", 1,"Message 1", "Message Value : " + i));

                //Round Robin
                myProducer.send(new ProducerRecord<String, String>("demo-topic", "Message Value : " + i));

                //Key-Hashing
                //myProducer.send(new ProducerRecord<String, String>("demo-topic","Message 1", "Message Value : " + i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            myProducer.close();
        }
    }
}
