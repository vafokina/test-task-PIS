package test.task.PIS.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class RabbitConfiguration {

    private ApplicationProperties applicationProperties;
    private ConnectionFactory connectionFactory;

    public RabbitConfiguration(ApplicationProperties applicationProperties,
                               ConnectionFactory connectionFactory) {
        this.applicationProperties = applicationProperties;
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ObjectMapper objectMapper, Queue queue) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter(objectMapper));
        rabbitTemplate.setRoutingKey(queue.getName());
        rabbitTemplate.setExchange(DirectExchange.DEFAULT.getName());
        return rabbitTemplate;
    }

    @Bean
    Queue queue() {
        return new Queue(applicationProperties.getQueues().getContractCreate());
    }

    @Bean
    Binding binding(Queue queue) {
        return BindingBuilder.bind(queue).to(DirectExchange.DEFAULT).with(queue.getName());
    }
}
