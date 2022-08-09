package test.task.PIS.routebuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;
import test.task.PIS.config.ApplicationProperties;
import test.task.PIS.dto.ContractStatus;
import test.task.PIS.service.ContractEventService;

@Component
public class RabbitMQRoute extends RouteBuilder {

    public static final String RABBIT_URI = "rabbitmq:amq.direct?queue=%s&routingKey=%s&autoDelete=false";

    private final ApplicationProperties applicationProperties;
    private final ContractEventService contractEventService;
    private final ObjectMapper objectMapper;

    public RabbitMQRoute(ApplicationProperties applicationProperties,
                         ContractEventService contractEventService,
                         ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.contractEventService = contractEventService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure() {
        String contractEventQueue = applicationProperties.getQueues().getContractEvent();

        JacksonDataFormat dataFormat = new JacksonDataFormat(objectMapper, ContractStatus.class);

        fromF(RABBIT_URI, contractEventQueue, contractEventQueue)
                .log("Before processing: ${body}")
                .unmarshal(dataFormat)
                .process(this::processMessage)
                .end();;
    }

    private void processMessage(Exchange exchange) {
        ContractStatus status = exchange.getMessage().getBody(ContractStatus.class);
        contractEventService.processEvent(status);
    }
}
