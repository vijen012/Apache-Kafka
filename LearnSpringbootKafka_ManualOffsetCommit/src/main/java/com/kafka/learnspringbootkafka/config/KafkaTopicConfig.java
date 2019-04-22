package com.kafka.learnspringbootkafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.broker}")
    private String bootStrapAddresses;

    @Value(value = "${kafka.topic}")
    private String kafkaTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddresses);
        return new KafkaAdmin(props);
    }

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(kafkaTopic, 4, (short) 1);
    }
}
