package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindProductByIdUseCase {
    private final ProductGateway productGateway;

    public Product execute(Long id) {
        return productGateway.findProductById(id);
    }
}
