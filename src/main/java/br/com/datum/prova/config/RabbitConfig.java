package br.com.datum.prova.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${queue.name}")
    private String queueName;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue myQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("exchange", true, false);
    }

    @Bean
    public Binding binding() {
        return new Binding(
                queueName,
                Binding.DestinationType.QUEUE,
                "my_exchange",
                queueName,
                null
        );
    }
}
