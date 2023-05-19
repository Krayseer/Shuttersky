package ru.krayseer.shuttersky.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    private final AmqpAdmin amqpAdmin;

    @Bean
    public Jackson2JsonMessageConverter jackson2Converter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
        final var jsonRabbitTemplate = new RabbitTemplate(connectionFactory);
        jsonRabbitTemplate.setMessageConverter(converter);
        return jsonRabbitTemplate;
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(new Queue("photoQueue", true));
    }
}
