package br.com.wise.commerce.product.usecase.strategy.generatesku.implementation;

import br.com.wise.commerce.product.gateway.ProductGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkuGeneratorStrategyImplementationTest {
    @Mock
    private ProductGateway productGateway;

    @InjectMocks
    private SkuGeneratorStrategyImplementation skuGenerator;

    @Test
    void shouldGenerateValidSkuWithCorrectFormat() {
        String productName = "Arroz";
        when(productGateway.existsBySku(anyString())).thenReturn(false);

        String sku = skuGenerator.generateSku(productName);

        assertThat(sku)
                .isNotNull()
                .startsWith("ARR")
                .endsWith("2025")
                .hasSize(15);
        verify(productGateway, atLeastOnce()).existsBySku(sku);
    }

    @Test
    void shouldRegenerateSkuIfAlreadyExists() {
        String productName = "Feijão";

        when(productGateway.existsBySku(anyString()))
                .thenReturn(true)
                .thenReturn(false);

        String sku = skuGenerator.generateSku(productName);

        assertThat(sku)
                .isNotNull()
                .startsWith("FEI")
                .endsWith("2025")
                .hasSize(15);
        verify(productGateway, atLeast(2)).existsBySku(anyString());
    }
}