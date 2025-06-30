package br.com.wise.commerce.product.application.configuration.rabbit;

import br.com.wise.commerce.product.application.dtos.rabbit.ProductMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public void send(ProductMessageDTO productMessage) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                productMessage
        );
    }

}
