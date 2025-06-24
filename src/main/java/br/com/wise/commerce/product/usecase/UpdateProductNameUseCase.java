package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductNameUseCase {
    private final ProductGateway productGateway;

    public void execute(String sku,String name){
        productGateway.updateProductName(sku, name);
    }
}
