package com.kafka.learnspringbootkafka.producer;

import com.kafka.learnspringbootkafka.data.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class ProducerKafka {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Person> personKafkaTemplate;

    @Autowired
    Environment env;

    public void sendMessage(String message){
        //ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(env.getProperty("kafka.topic"),message,message);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(env.getProperty("kafka.topic"), message);
        future.addCallback(
                new ListenableFutureCallback<SendResult<String,String>>() {

                    @Override
                    public void onFailure(Throwable ex) {
                        System.out.println("Inside Exception");

                    }

                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        // TODO Auto-generated method stub
                        System.out.println("Inside Success : SendMessage()");

                    }
                });

    }


    public void sendPersonData(Person person) {
        //ListenableFuture<SendResult<String, String>> future = personKafkaTemplate.send(env.getProperty("kafka.topic"),message,message);
        ListenableFuture<SendResult<String, Person>> future = personKafkaTemplate.send(env.getProperty("kafka.person.topic"), person);
        future.addCallback(
                new ListenableFutureCallback<SendResult<String,Person>>() {

                    @Override
                    public void onFailure(Throwable ex) {
                        System.out.println("Inside Exception");

                    }

                    @Override
                    public void onSuccess(SendResult<String, Person> result) {
                        // TODO Auto-generated method stub
                        System.out.println("Inside Success: SendPersonaData()");

                    }
                });
    }

}