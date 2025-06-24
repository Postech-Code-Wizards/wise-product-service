package br.com.wise.commerce.product.converter;

import br.com.wise.commerce.product.application.dtos.request.ProductRequest;
import br.com.wise.commerce.product.application.dtos.response.ProductResponse;
import br.com.wise.commerce.product.domain.Category;
import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.gateway.database.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductConverterTest {
    private ProductConverter productConverter;

    @BeforeEach
    void setup() {
        productConverter = new ProductConverter();
    }

    @Test
    void shouldConvertFromRequestToDomain() {
        ProductRequest request = new ProductRequest(
                "Arroz",
                "Arroz Branco",
                "ALIMENTOS",
                15.0
        );
        Product product = productConverter.toDomain(request);

        assertThat(product.getName()).isEqualTo("Arroz");
        assertThat(product.getDescription()).isEqualTo("Arroz Branco");
        assertThat(product.getCategory()).isEqualTo(Category.ALIMENTOS);
        assertThat(product.getPrice()).isEqualTo(15.0);
    }

    @Test
    void shouldConvertFromDomainToEntity() {
        ZonedDateTime createdAt = ZonedDateTime.of(2025, 6, 15, 14, 0, 0, 0, ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime updatedAt = ZonedDateTime.of(2025, 6, 15, 15, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));

        Product product = new Product(
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

        ProductEntity productEntity = productConverter.toEntity(product);

        assertThat(productEntity.getId()).isEqualTo(1L);
        assertThat(productEntity.getName()).isEqualTo("Arroz");
        assertThat(productEntity.getDescription()).isEqualTo("Arroz Branco");
        assertThat(productEntity.getSku()).isEqualTo("ARR123qwer2025");
        assertThat(productEntity.getCategory()).isEqualTo(Category.ALIMENTOS);
        assertThat(productEntity.getPrice()).isEqualTo(15.0);
        assertThat(productEntity.getInStock()).isTrue();
        assertThat(productEntity.getStock()).isEqualTo(1000);
        assertThat(productEntity.getCreatedAt()).isEqualTo(createdAt);
        assertThat(productEntity.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldConvertFromEntityToDomain() {
        ZonedDateTime createdAt = ZonedDateTime.of(2025, 6, 15, 14, 0, 0, 0, ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime updatedAt = ZonedDateTime.of(2025, 6, 15, 15, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));

        ProductEntity productEntity = new ProductEntity(
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

        Product product = productConverter.toDomain(productEntity);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("Arroz");
        assertThat(product.getDescription()).isEqualTo("Arroz Branco");
        assertThat(product.getSku()).isEqualTo("ARR123qwer2025");
        assertThat(product.getCategory()).isEqualTo(Category.ALIMENTOS);
        assertThat(product.getPrice()).isEqualTo(15.0);
        assertThat(product.getInStock()).isTrue();
        assertThat(product.getStock()).isEqualTo(1000);
        assertThat(product.getCreatedAt()).isEqualTo(createdAt);
        assertThat(product.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldConvertFromDomainToResponse() {
        ZonedDateTime createdAt = ZonedDateTime.of(2025, 6, 15, 14, 0, 0, 0, ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime updatedAt = ZonedDateTime.of(2025, 6, 15, 15, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));

        Product product = new Product(
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

        ProductResponse productResponse = productConverter.toResponse(product);

        assertThat(productResponse.id()).isEqualTo(1L);
        assertThat(productResponse.name()).isEqualTo("Arroz");
        assertThat(productResponse.description()).isEqualTo("Arroz Branco");
        assertThat(productResponse.sku()).isEqualTo("ARR123qwer2025");
        assertThat(productResponse.category()).isEqualTo(Category.ALIMENTOS);
        assertThat(productResponse.price()).isEqualTo(15.0);
        assertThat(productResponse.inStock()).isTrue();
        assertThat(productResponse.stock()).isEqualTo(1000);
        assertThat(productResponse.createdAt()).isEqualTo(createdAt);
        assertThat(productResponse.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldThrowExceptionWhenInvalidCategory() {
        ProductRequest request = new ProductRequest("Arroz", "Desc", "INVALIDA", 15.0);

        assertThatThrownBy(() -> productConverter.toDomain(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No enum constant");
    }
}