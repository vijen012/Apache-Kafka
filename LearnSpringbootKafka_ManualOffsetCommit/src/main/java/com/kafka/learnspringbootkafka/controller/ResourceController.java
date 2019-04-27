package com.kafka.learnspringbootkafka.controller;

import com.kafka.learnspringbootkafka.data.Person;
import com.kafka.learnspringbootkafka.producer.ProducerKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @Autowired
    private Environment environment;

    @Autowired
    private ProducerKafka producerKafka;

    @GetMapping(value = "/home")
    public String getResponse(@RequestParam("input") String value) {
        producerKafka.sendMessage(value);
        return environment.getProperty("message.response");
    }

    @GetMapping(value = "/person")
    public String getPersonInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        producerKafka.sendPersonData(person);
        return firstName +" "+ lastName;
    }
}
