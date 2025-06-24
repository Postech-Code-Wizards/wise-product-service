package br.com.wise.commerce.product.application.controller;

import br.com.wise.commerce.product.application.controller.exceptions.ProductException;
import br.com.wise.commerce.product.converter.ProductConverter;
import br.com.wise.commerce.product.domain.Product;
import br.com.wise.commerce.product.application.dtos.request.ProductRequest;
import br.com.wise.commerce.product.application.dtos.response.ProductResponse;
import br.com.wise.commerce.product.usecase.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductResolver {
    private static final Logger logger = LogManager.getLogger(ProductResolver.class);

    private final CreateProductUseCase createProductUseCase;
    private final FindProductByIdUseCase findProductByIdUseCase;
    private final FindProductsListByNameAndPaginationUseCase findProductsListByNameAndPaginationUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;
    private final UpdateProductDescriptionUseCase updateProductDescriptionUseCase;
    private final UpdateProductPriceUseCase updateProductPriceUseCase;
    private final DeleteProductByIdUseCase deleteProductByIdUseCase;

    private final ProductConverter productConverter;

    @MutationMapping
    public void createProduct(@Argument("input") ProductRequest productRequest) {
        createProductUseCase.execute(productConverter.toDomain(productRequest));
    }

    @QueryMapping
    public ProductResponse getProductById(@Argument("input") Long id) {
        var domain = findProductByIdUseCase.execute(id);
        return productConverter.toResponse(domain);
    }

    @QueryMapping
    public List<ProductResponse> getProductsList(@Argument("name") String name,
                                                 @Argument("page") Optional<Integer> page,
                                                 @Argument("size") Optional<Integer> size
    ) {
        int defaultPage = page.orElse(0);
        int defaultSize = size.orElse(10);

        List<Product> domain = findProductsListByNameAndPaginationUseCase.execute(name, defaultPage, defaultSize);
        return domain.stream().map(productConverter::toResponse).toList();
    }

    @MutationMapping
    public void updateProductName(@Argument("sku") String sku,
                                  @Argument("name") String name) {
        if (name != null) {
            updateProductNameUseCase.execute(sku, name);
        } else {
            logger.error("Product name is null in update api for product");
            throw new ProductException("Product name is null");
        }
    }

    @MutationMapping
    public void updateProductDescription(@Argument("sku") String sku,
                                         @Argument("description") String description) {
        if (description != null) {
            updateProductDescriptionUseCase.execute(sku, description);
        } else {
            logger.error("Product description is null in update api for product");
            throw new ProductException("Product description is null");
        }
    }

    @MutationMapping
    public void updateProductPrice(@Argument("sku") String sku,
                                   @Argument("price") Double price) {
        if (price != null) {
            updateProductPriceUseCase.execute(sku, price);
        } else {
            logger.error("Product price is null in update api for product");
            throw new ProductException("Product price is null");
        }
    }

    @MutationMapping
    public void deleteProductById(@Argument("id") Long id) {
        if (id != null) {
            deleteProductByIdUseCase.execute(id);
        } else {
            logger.error("Id used for delete product is null");
            throw new ProductException("Id used for delete product is null");
        }
    }

}
