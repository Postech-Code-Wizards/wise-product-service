package br.com.wise.commerce.product.usecase.strategy.generatesku;

public interface SkuGeneratorStrategy {
    String generateSku(String productName);
}
