package br.com.wise.commerce.product.gateway.database;

import br.com.wise.commerce.product.application.controller.exceptions.ProductNotFoundException;
import br.com.wise.commerce.product.converter.ProductConverter;
import br.com.wise.commerce.product.domain.Category;
import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.gateway.database.entities.ProductEntity;
import br.com.wise.commerce.product.gateway.database.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductJpaGatewayTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductConverter productConverter;

    @InjectMocks
    private ProductJpaGateway productJpaGateway;

    @Test
    void shouldReturnTrueWhenSkuExists() {
        when(productRepository.existsBySku("ABC123")).thenReturn(true);
        boolean exists = productJpaGateway.existsBySku("ABC123");
        assertThat(exists).isTrue();
    }

    @Test
    void shouldCreateProduct() {
        Product product = mock(Product.class);
        ProductEntity entity = mock(ProductEntity.class);

        when(productConverter.toEntity(product)).thenReturn(entity);

        productJpaGateway.createProduct(product);

        verify(productRepository).save(entity);
    }

    @Test
    void shouldFindProductById() {
        ZonedDateTime createdAt = ZonedDateTime.of(2025, 6, 15, 14, 0, 0, 0, ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime updatedAt = ZonedDateTime.of(2025, 6, 15, 15, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));

        ProductEntity entity = new ProductEntity();
        Product domain = new Product(
                1L,
                "Arroz",
                "Arroz Branco",
                "ARR123qwer2025",
                Category.ALIMENTOS,
                15.0,
                true,
                1000,
                createdAt,
                updatedAt
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(productConverter.toDomain(entity)).thenReturn(domain);

        Product result = productJpaGateway.findProductById(1L);

        assertThat(result).isEqualTo(domain);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundById() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productJpaGateway.findProductById(1L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product not found for sku 1");
    }

    @Test
    void shouldFindProductsListByNameAndPagination() {
        ZonedDateTime createdAt = ZonedDateTime.of(2025, 6, 15, 14, 0, 0, 0, ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime updatedAt = ZonedDateTime.of(2025, 6, 15, 15, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));

        ProductEntity entity = new ProductEntity();
        Product domain = new Product(
                1L,
                "Arroz",
                "Arroz Branco",
                "ARR123qwer2025",
                Category.ALIMENTOS,
                15.0,
                true,
                1000,
                createdAt,
                updatedAt
        );
        List<ProductEntity> entities = List.of(entity);

        Page<ProductEntity> page = new PageImpl<>(entities);

        when(productRepository.findByNameContainingIgnoreCase(eq("prod"), any(PageRequest.class))).thenReturn(page);
        when(productConverter.toDomain(entity)).thenReturn(domain);

        List<Product> result = productJpaGateway.findProductsListByNameAndPagination("prod", 0, 10);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(domain);
    }

    @Test
    void shouldUpdateProductName() {
        ProductEntity entity = new ProductEntity();
        when(productRepository.findBySku("SKU123")).thenReturn(Optional.of(entity));

        productJpaGateway.updateProductName("SKU123", "New Name");

        assertThat(entity.getName()).isEqualTo("New Name");
    }

    @Test
    void shouldUpdateProductDescription() {
        ProductEntity entity = new ProductEntity();
        when(productRepository.findBySku("SKU123")).thenReturn(Optional.of(entity));

        productJpaGateway.updateProductDescription("SKU123", "New Desc");

        assertThat(entity.getDescription()).isEqualTo("New Desc");
    }

    @Test
    void shouldUpdateProductPrice() {
        ProductEntity entity = new ProductEntity();
        when(productRepository.findBySku("SKU123")).thenReturn(Optional.of(entity));

        productJpaGateway.updateProductPrice("SKU123", 99.99);

        assertThat(entity.getPrice()).isEqualTo(99.99);
    }

    @Test
    void shouldDeleteProductById() {
        productJpaGateway.deleteProductById(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenUpdateProductNameWithInvalidSku() {
        when(productRepository.findBySku("INVALID")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productJpaGateway.updateProductName("INVALID", "Name"))
                .isInstanceOf(ProductNotFoundException.class);
    }
}