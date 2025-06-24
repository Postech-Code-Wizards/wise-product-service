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
class UpdateProductNameUseCaseTest {
    @Mock
    ProductGateway productGateway;

    @InjectMocks
    UpdateProductNameUseCase updateProductNameUseCase;

    @Test
    void shouldUpdateProductName() {
        String sku = "ARRfd15e6t2025";
        String name = "Arroz Integral";

        updateProductNameUseCase.execute(sku, name);

        ArgumentCaptor<String> skuCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        verify(productGateway).updateProductName(skuCaptor.capture(), nameCaptor.capture());

        assertEquals(sku, skuCaptor.getValue());
        assertEquals(name, nameCaptor.getValue());
    }
}