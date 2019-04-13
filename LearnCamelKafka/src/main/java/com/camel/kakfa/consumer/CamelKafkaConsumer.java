package com.camel.kakfa.consumer;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class CamelKafkaConsumer {

    public static void main(String[] args) {
        final CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("kafka:my-topic?brokers=localhost:9092"
                    + "&consumersCount=1"
                    + "&seekTo=beginning"
                    + "&groupId=group1")
                            .routeId("FromKafka")
                            .log("${body}");

                }
            });
            camelContext.start();
            //run for 5 min
            Thread.sleep(5*60*1000);
            camelContext.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
