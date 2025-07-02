package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindProductBySkuUseCase {
    private final ProductGateway productGateway;

    public Product execute(String sku){
        return productGateway.findProductBySku(sku);
    }
}
