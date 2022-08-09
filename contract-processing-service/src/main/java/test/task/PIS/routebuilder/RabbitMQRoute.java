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
import test.task.PIS.dto.CreateNewContract;
import test.task.PIS.service.ContractProcessingService;

@Component
public class RabbitMQRoute extends RouteBuilder {

    public static final String RABBIT_URI = "rabbitmq:amq.direct?queue=%s&routingKey=%s&autoDelete=false";

    private final ApplicationProperties applicationProperties;
    private final ContractProcessingService contractProcessingService;
    private final ObjectMapper objectMapper;

    public RabbitMQRoute(ApplicationProperties applicationProperties,
                         ContractProcessingService contractProcessingService,
                         ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.contractProcessingService = contractProcessingService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure() {
        String contractCreateQueue = applicationProperties.getQueues().getContractCreate();
        String contractEventQueue = applicationProperties.getQueues().getContractEvent();

        JacksonDataFormat contractDataFormat = new JacksonDataFormat(objectMapper, CreateNewContract.class);
        JacksonDataFormat statusDataFormat = new JacksonDataFormat(objectMapper, ContractStatus.class);

        fromF(RABBIT_URI, contractCreateQueue, contractCreateQueue)
                .log("Before processing: ${body}")
                .unmarshal(contractDataFormat)
                .process(this::processMessage)
                .log( "After processing: ${body}")
                .marshal(statusDataFormat)
                .toF(RABBIT_URI, contractEventQueue, contractEventQueue)
                .end();
    }

    private void processMessage(Exchange exchange) {
        CreateNewContract contract = exchange.getMessage().getBody(CreateNewContract.class);
        ContractStatus status = contractProcessingService.processNewContract(contract);

        Message message = new DefaultMessage(exchange);
        message.setBody(status);
        exchange.setMessage(message);
    }
}
