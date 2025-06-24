package br.com.wise.commerce.product.usecase;

import br.com.wise.commerce.product.gateway.ProductGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteProductByIdUseCaseTest {

    @Mock
    ProductGateway productGateway;

    @InjectMocks
    DeleteProductByIdUseCase deleteProductByIdUseCase;

    @Test
    void shouldDeleteProductUsingId() {
        Long id = 1L;
        deleteProductByIdUseCase.execute(id);
        verify(productGateway).deleteProductById(id);
    }
}