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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllProductsUseCaseTest {
    @Mock
    ProductGateway productGateway;

    @InjectMocks
    FindAllProductsUseCase findAllProductsUseCase;

    @Test
    void shouldReturnAListOfProductsWithPagination() {
        Product expectedProduct = new Product(
                1L,
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
        when(productGateway.findAllProducts()).thenReturn(List.of(expectedProduct));

        List<Product> result = findAllProductsUseCase.execute();
        Product actual = result.get(0);
        assertEquals(1, result.size());
        assertEquals("Arroz", actual.getName());
        assertEquals("Arroz branco", actual.getDescription());
        assertEquals("ARRfd15e6t2025", actual.getSku());
        assertEquals(Category.ALIMENTOS, actual.getCategory());
        assertEquals(15.0, actual.getPrice());
        assertEquals(true, actual.getInStock());
        assertEquals(1000, actual.getStock());
        assertEquals(ZonedDateTime.of(2025, 6, 15, 16, 30, 0, 0, ZoneId.of("America/Sao_Paulo")), actual.getCreatedAt());
        assertEquals(ZonedDateTime.of(2025, 12, 31, 23, 59, 59, 0, ZoneId.of("America/Sao_Paulo")), actual.getUpdatedAt());

    }
}