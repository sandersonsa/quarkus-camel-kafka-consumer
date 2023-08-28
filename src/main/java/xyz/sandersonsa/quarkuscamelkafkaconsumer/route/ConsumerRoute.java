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
        from("kafka:{{kafka.topic.name}}")
                .routeId("FromKafka2Seda")
                .log("Received : \"${body}\"")
                .to("seda:kafka-messages");
    }
}
