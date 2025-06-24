package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.gateway.ProductGateway;
import br.com.wise.commerce.product.usecase.strategy.generatesku.SkuGeneratorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProductUseCase {
    private final ProductGateway productGateway;
    private final SkuGeneratorStrategy skuGeneratorStrategy;

    public void execute(Product product) {
        product.createSku(skuGeneratorStrategy.generateSku(product.getName()));
        productGateway.createProduct(product);
    }
}
