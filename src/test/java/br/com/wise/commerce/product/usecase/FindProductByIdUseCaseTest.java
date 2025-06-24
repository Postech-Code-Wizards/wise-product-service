package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.domain.Category;
import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.gateway.ProductGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindProductByIdUseCaseTest {
    @Mock
    ProductGateway productGateway;

    @InjectMocks
    FindProductByIdUseCase findProductByIdUseCase;

    @Test
    void shouldFindProductUsingProvidedId() {
        Long id = 1L;
        Product expectedProduct = new Product(
                id,
                "Arroz",
                "Arroz branco",
                "ARRfd15e6t2025",
                Category.ALIMENTOS,
                15.0,
                true,
                1000,
                ZonedDateTime.of(2025, 6, 15, 16, 30, 0, 0, ZoneId.of("America/Sao_Paulo")),
                ZonedDateTime.of(2025, 12, 31, 23, 59, 59, 0, ZoneId.of("America/Sao_Paulo"))
        );
        when(productGateway.findProductById(id)).thenReturn(expectedProduct);

        Product result = findProductByIdUseCase.execute(id);

        verify(productGateway).findProductById(id);
        assertEquals(expectedProduct,result);
    }


}