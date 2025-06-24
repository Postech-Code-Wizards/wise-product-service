package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.gateway.ProductGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateProductPriceUseCaseTest {
    @Mock
    ProductGateway productGateway;

    @InjectMocks
    UpdateProductPriceUseCase updateProductPriceUseCase;

    @Test
    void shouldUpdateProductName() {
        String sku = "ARRfd15e6t2025";
        Double price = 17.0;

        updateProductPriceUseCase.execute(sku, price);

        ArgumentCaptor<String> skuCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
        verify(productGateway).updateProductPrice(skuCaptor.capture(), priceCaptor.capture());

        assertEquals(sku, skuCaptor.getValue());
        assertEquals(price, priceCaptor.getValue());
    }
}