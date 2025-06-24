package br.com.wise.commerce.product.usecase.strategy.generatesku.implementation;

import br.com.wise.commerce.product.gateway.ProductGateway;
import br.com.wise.commerce.product.usecase.strategy.generatesku.SkuGeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SkuGeneratorStrategyImplementation implements SkuGeneratorStrategy {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    private final ProductGateway productGateway;

    public String generateSku(String productName) {
        String skuGenerated;
        do {
            String prefix = productName.substring(0, 3).toUpperCase();
            String middle = generateMiddleAlphaNumeric();
            String end = String.valueOf(LocalDate.now().getYear());
            skuGenerated = prefix + middle + end;
        } while (productGateway.existsBySku(skuGenerated));
        return skuGenerated;
    }

    private String generateMiddleAlphaNumeric() {
        StringBuilder stringBuilder = new StringBuilder(7);
        for (int i = 0; i <= 7; i++) {
            stringBuilder.append(CHARACTERS.charAt(secureRandom.nextInt(CHARACTERS.length())));
        }
        return stringBuilder.toString();
    }
}
