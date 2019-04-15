package com.kafka.learnspringbootkafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private Environment environment;

    @GetMapping(value = "/home")
    public String getResponse() {
        return environment.getProperty("message.response");
    }
}
