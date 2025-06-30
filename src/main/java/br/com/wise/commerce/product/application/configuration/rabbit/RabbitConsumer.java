package br.com.wise.commerce.product.application.configuration.rabbit;

import br.com.wise.commerce.product.application.dtos.rabbit.ProductMessageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {
    private static final Logger logger = LogManager.getLogger(RabbitConsumer.class);
    private static final String PRODUCT_MESSAGE = "Message produced: ";

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receive (ProductMessageDTO productMessage){
        logger.info(PRODUCT_MESSAGE + "{}", productMessage);
    }
}
