package br.com.wise.commerce.product.gateway.database;

import br.com.wise.commerce.product.application.controller.exceptions.ProductNotFoundException;
import br.com.wise.commerce.product.converter.ProductConverter;
import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.gateway.ProductGateway;
import br.com.wise.commerce.product.gateway.database.entities.ProductEntity;
import br.com.wise.commerce.product.gateway.database.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductJpaGateway implements ProductGateway {
    private static final String PRODUCT_NOT_FOUND = "Product not found for sku ";
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }

    public void createProduct(Product product) {
        var productEntity = productConverter.toEntity(product);
        productRepository.save(productEntity);
    }

    public Product findProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(()
                -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
        return productConverter.toDomain(productEntity);
    }

    public List<Product> findProductsListByNameAndPagination(String productName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> productEntityPage = productRepository.findByNameContainingIgnoreCase(productName, pageable);
        return productEntityPage.getContent().stream().map(productConverter::toDomain).toList();
    }

    public void updateProductName(String sku, String name) {
        ProductEntity productEntity = productRepository.findBySku(sku).orElseThrow(()
                -> new ProductNotFoundException(PRODUCT_NOT_FOUND + sku));
        productEntity.setName(name);
    }

    public void updateProductDescription(String sku, String description) {
        ProductEntity productEntity = productRepository.findBySku(sku).orElseThrow(()
                -> new ProductNotFoundException(PRODUCT_NOT_FOUND + sku));
        productEntity.setDescription(description);
    }

    public void updateProductPrice(String sku, Double price) {
        ProductEntity productEntity = productRepository.findBySku(sku).orElseThrow(()
                -> new ProductNotFoundException(PRODUCT_NOT_FOUND + sku));
        productEntity.setPrice(price);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
