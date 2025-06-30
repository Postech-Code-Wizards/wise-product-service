package br.com.wise.commerce.product.application.controller.rabbit;

import br.com.wise.commerce.product.application.configuration.rabbit.RabbitProducer;
import br.com.wise.commerce.product.application.dtos.rabbit.ProductMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rabbit")
@RequiredArgsConstructor
public class RabbitController {
    private final RabbitProducer producer;

    @PostMapping
    public ResponseEntity<Void> send(@RequestBody ProductMessageDTO productMessageDTO) {
        producer.send(productMessageDTO);
        return ResponseEntity.ok().build();
    }
}
