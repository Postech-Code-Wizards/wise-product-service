package br.com.wise.commerce.product.converter;

import br.com.wise.commerce.product.domain.Category;
import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.application.dtos.request.ProductRequest;
import br.com.wise.commerce.product.application.dtos.response.ProductResponse;
import br.com.wise.commerce.product.gateway.database.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public Product toDomain(ProductRequest productRequest) {
        return new Product(
                null,
                productRequest.name(),
                productRequest.description(),
                null,
                Category.valueOf(productRequest.category().toUpperCase()),
                productRequest.price(),
                null,
                null,
                null,
                null
        );
    }

    public Product toDomain(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getSku(),
                productEntity.getCategory(),
                productEntity.getPrice(),
                productEntity.getInStock(),
                productEntity.getStock(),
                productEntity.getCreatedAt(),
                productEntity.getUpdatedAt()
        );
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getCategory(),
                product.getPrice(),
                product.getInStock(),
                product.getStock(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getCategory(),
                product.getPrice(),
                product.getInStock(),
                product.getStock(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
