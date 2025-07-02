package br.com.wise.commerce.product.gateway;

import br.com.wise.commerce.product.domain.Product;

import java.util.List;

public interface ProductGateway {
    boolean existsBySku(String sku);

    void createProduct(Product product);

    Product findProductById(Long id);

    Product findProductBySku(String sku);

    List<Product> findProductsListByNameAndPagination(String productName, int page, int size);

    List<Product> findAllProducts();

    void updateProductName(String sku, String name);

    void updateProductDescription(String sku, String description);

    void updateProductPrice(String sku, Double price);

    void deleteProductById(Long id);
}
