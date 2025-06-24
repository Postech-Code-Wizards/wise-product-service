package br.com.wise.commerce.product.application.dtos.response;

import br.com.wise.commerce.product.domain.Category;

import java.time.ZonedDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String sku,
        Category category,
        Double price,
        Boolean inStock,
        Integer stock,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {
}
