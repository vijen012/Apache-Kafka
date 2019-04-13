package com.camel.kafka.producer;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.HashMap;
import java.util.Map;

public class CamelKafkaProducer {
    public static void main(String[] args) {
        final CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    final KafkaComponent kafkaComponent = new KafkaComponent();
                    kafkaComponent.setBrokers("localhost:9092");
                    camelContext.addComponent("kafka", kafkaComponent);
                    from("direct:pushtoTopic").routeId("DirectToKafka")
                            .to("kafka:my-topic").log("${headers}");

                }
            });

            final ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            camelContext.start();
            final Map<String, Object> headers = new HashMap<>();
            headers.put(KafkaConstants.PARTITION_KEY, 0);
            headers.put(KafkaConstants.KEY, "1");
            for(int i=0;i<=5;i++){
                producerTemplate.sendBodyAndHeaders("direct:pushtoTopic"," Hi Hello " + i, headers);
            }


            //run for 5 min
            Thread.sleep(5*60*1000);
            camelContext.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
