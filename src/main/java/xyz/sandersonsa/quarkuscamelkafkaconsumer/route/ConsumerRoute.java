package xyz.sandersonsa.quarkuscamelkafkaconsumer.route;

import javax.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ConsumerRoute extends RouteBuilder {

    private static final Logger LOG = Logger.getLogger(ConsumerRoute.class);

    @Override
    public void configure() throws Exception {

        // kafka consumer
        // from("kafka:{{kafka.topic.name}}")

        from("kafka:{{kafka.topic.name}}?brokers={{app.kafka.brokers}}"
                // Setup the topic and broker address
                + "&groupId={{app.consumer.groupid}}"
                // The consumer processor group ID
                + "&autoOffsetReset=earliest"
                // Ask to start from the beginning if we have unknown offset
                // + "&offsetRepository=#offsetRepo"
                )
                // Keep the offsets in the previously configured repository
                .routeId("FromKafka2Seda")
                // .log("Received : \"${body}\"")
                .log("#MESSAGE RECEIVED FROM KAFKA : ${body}")
                .log("#on the topic ${headers[kafka.TOPIC]}")
                .log("#on the partition ${headers[kafka.PARTITION]}")
                .log("#with the offset ${headers[kafka.OFFSET]}")
                .log("#with the key ${headers[kafka.KEY]}")
                .log("#with source ${headers[source]}\n")
                .to("seda:kafka-messages");


    }
}
