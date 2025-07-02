package br.com.wise.commerce.product.application.controller;

import br.com.wise.commerce.product.application.controller.exceptions.ProductException;
import br.com.wise.commerce.product.application.dtos.request.ProductRequest;
import br.com.wise.commerce.product.application.dtos.response.ProductResponse;
import br.com.wise.commerce.product.converter.ProductConverter;
import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.usecase.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductResolverTest {
    @Mock
    private CreateProductUseCase createProductUseCase;
    @Mock
    private FindProductByIdUseCase findProductByIdUseCase;
    @Mock
    private FindProductBySkuUseCase findProductBySkuUseCase;
    @Mock
    private FindProductsListByNameAndPaginationUseCase findProductsListByNameAndPaginationUseCase;
    @Mock
    private FindAllProductsUseCase findAllProductsUseCase;
    @Mock
    private UpdateProductNameUseCase updateProductNameUseCase;
    @Mock
    private UpdateProductDescriptionUseCase updateProductDescriptionUseCase;
    @Mock
    private UpdateProductPriceUseCase updateProductPriceUseCase;
    @Mock
    private DeleteProductByIdUseCase deleteProductByIdUseCase;
    @Mock
    private ProductConverter productConverter;
    @InjectMocks
    private ProductResolver productResolver;

    @Test
    void shouldCallCreateProductUseCase() {
        ProductRequest request = new ProductRequest("Arroz", "Arroz Branco", "ALIMENTOS", 15.0);
        Product product = mock(Product.class);

        when(productConverter.toDomain(request)).thenReturn(product);

        productResolver.createProduct(request);

        verify(createProductUseCase).execute(product);
    }

    @Test
    void shouldReturnProductById() {
        Long id = 1L;
        Product domain = mock(Product.class);
        ProductResponse response = mock(ProductResponse.class);

        when(findProductByIdUseCase.execute(id)).thenReturn(domain);
        when(productConverter.toResponse(domain)).thenReturn(response);

        ProductResponse result = productResolver.getProductById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldReturnProductBySku(){
        String sku = "ARR123qwer2025";
        Product domain = mock(Product.class);
        ProductResponse response = mock(ProductResponse.class);

        when(findProductBySkuUseCase.execute(sku)).thenReturn(domain);
        when(productConverter.toResponse(domain)).thenReturn(response);

        ProductResponse result = productResolver.findProductBySku(sku);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldReturnPaginatedProductList() {
        String name = "Arroz";
        List<Product> domainList = List.of(mock(Product.class));
        List<ProductResponse> responseList = List.of(mock(ProductResponse.class));

        when(findProductsListByNameAndPaginationUseCase.execute(name, 0, 10)).thenReturn(domainList);
        when(productConverter.toResponse(any())).thenReturn(responseList.get(0));

        List<ProductResponse> result = productResolver.findProductsListByNameAndPagination(name, Optional.empty(), Optional.empty());

        assertThat(result).hasSize(1);
        verify(productConverter).toResponse(domainList.get(0));
    }

    @Test
    void shouldReturnProductList() {
        List<Product> domainList = List.of(mock(Product.class));
        List<ProductResponse> responseList = List.of(mock(ProductResponse.class));

        when(findAllProductsUseCase.execute()).thenReturn(domainList);
        when(productConverter.toResponse(any())).thenReturn(responseList.get(0));

        List<ProductResponse> result = productResolver.findAllProducts();

        assertThat(result).hasSize(1);
        verify(productConverter).toResponse(domainList.get(0));
    }

    @Test
    void shouldUpdateProductName() {
        productResolver.updateProductName("SKU123", "Feijão");

        verify(updateProductNameUseCase).execute("SKU123", "Feijão");
    }

    @Test
    void shouldThrowWhenProductNameIsNull() {
        assertThrows(ProductException.class, () -> productResolver.updateProductName("SKU123", null));
    }

    @Test
    void shouldUpdateProductDescription() {
        productResolver.updateProductDescription("SKU123", "Novo desc");

        verify(updateProductDescriptionUseCase).execute("SKU123", "Novo desc");
    }

    @Test
    void shouldThrowWhenProductDescriptionIsNull() {
        assertThrows(ProductException.class, () -> productResolver.updateProductDescription("SKU123", null));
    }

    @Test
    void shouldUpdateProductPrice() {
        productResolver.updateProductPrice("SKU123", 19.99);

        verify(updateProductPriceUseCase).execute("SKU123", 19.99);
    }

    @Test
    void shouldThrowWhenProductPriceIsNull() {
        assertThrows(ProductException.class, () -> productResolver.updateProductPrice("SKU123", null));
    }

    @Test
    void shouldDeleteProductById() {
        productResolver.deleteProductById(1L);

        verify(deleteProductByIdUseCase).execute(1L);
    }

    @Test
    void shouldThrowWhenDeleteProductIdIsNull() {
        assertThrows(ProductException.class, () -> productResolver.deleteProductById(null));
    }
}