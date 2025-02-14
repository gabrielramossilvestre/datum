package br.com.datum.prova.message;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(queue.getName(), message);
        System.out.println("Mensagem enviada: " + message);
    }
}
