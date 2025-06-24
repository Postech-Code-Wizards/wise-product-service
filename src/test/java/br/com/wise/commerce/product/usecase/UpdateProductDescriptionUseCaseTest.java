package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.gateway.ProductGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateProductDescriptionUseCaseTest {

    @Mock
    ProductGateway productGateway;

    @InjectMocks
    UpdateProductDescriptionUseCase updateProductDescriptionUseCase;

    @Test
    void shouldUpdateProductDescription() {
        String sku = "ARRfd15e6t2025";
        String description = "Arroz branco prato fino";

        updateProductDescriptionUseCase.execute(sku, description);

        ArgumentCaptor<String> skuCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> descriptionCaptor = ArgumentCaptor.forClass(String.class);
        verify(productGateway).updateProductDescription(skuCaptor.capture(), descriptionCaptor.capture());

        assertEquals(sku, skuCaptor.getValue());
        assertEquals(description, descriptionCaptor.getValue());
    }
}