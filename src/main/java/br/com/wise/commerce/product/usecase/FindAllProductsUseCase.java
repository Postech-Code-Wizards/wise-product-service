package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllProductsUseCase {
    private final ProductGateway productGateway;

    public List<Product> execute(){
        return productGateway.findAllProducts();
    }
}
