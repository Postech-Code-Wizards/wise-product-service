package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.domain.Category;
import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.gateway.ProductGateway;
import br.com.wise.commerce.product.usecase.strategy.generatesku.SkuGeneratorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {
    @Mock
    private ProductGateway productGateway;
    @Mock
    private SkuGeneratorStrategy skuGeneratorStrategy;
    @InjectMocks
    private CreateProductUseCase createProductUseCase;


    @Test
    void shouldCreateProductWithGeneratedSku() {
        Product product = new Product(null, "Arroz", null, null, Category.ALIMENTOS, 15.0, null, null, null, null);
        when(skuGeneratorStrategy.generateSku("Arroz")).thenReturn("ARRfd15e6t2025");

        createProductUseCase.execute(product);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productGateway).createProduct(captor.capture());

        Product enviado = captor.getValue();
        assertEquals("Arroz", enviado.getName());
        assertEquals("ARRfd15e6t2025", enviado.getSku());
        assertEquals(Category.ALIMENTOS, enviado.getCategory());
    }
}